package com.zxw.delay.event;

import com.zxw.constants.RedisKeyConsts;
import com.zxw.delay.entity.Job;
import com.zxw.delay.entity.JobPool;
import com.zxw.delay.entity.ReadyQueue;
import com.zxw.delay.enums.DelayJobEnums;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnClass(DelayBucket.class)
public class DelayTimer {
    @Autowired
    private DelayBucket delayBucket;
    @Autowired
    private JobPool jobPool;
    @Autowired
    private ReadyQueue readyQueue;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private int bucketsSize = 3;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(bucketsSize, bucketsSize, 0L, TimeUnit.HOURS, new LinkedBlockingQueue<>());

    @PostConstruct
    public void init() {
        for (int i = 0; i < bucketsSize; i++) {
            threadPoolExecutor.execute(new DistributeThread(i));
        }
    }

    class DistributeThread implements Runnable {
        private String name;

        public DistributeThread(int index) {
            this.name = RedisKeyConsts.DELAY_BUCKET + "_" + index;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Set<String> queue = redisTemplate.opsForZSet().rangeByScore(this.name, 0, System.currentTimeMillis());
                    if (CollectionUtils.isNotEmpty(queue)) {
                        queue.forEach(k -> {
                            Job job = jobPool.getDelayJob(k);
                            if (StringUtils.equals(job.getStatus(), DelayJobEnums.DELAY.getValue())) {
                                job.setStatus(DelayJobEnums.READY.getValue());
                                readyQueue.pushJob(job);
                                redisTemplate.opsForZSet().remove(this.name, k);
                            }
                        });
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}