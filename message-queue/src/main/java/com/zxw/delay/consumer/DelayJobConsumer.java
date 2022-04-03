package com.zxw.delay.consumer;

import com.zxw.delay.entity.Job;
import com.zxw.delay.entity.JobPool;
import com.zxw.delay.entity.ReadyQueue;
import com.zxw.delay.enums.DelayJobEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @author zxw
 * @date 2022/4/3 16:17
 */
@Component
@Slf4j
public class DelayJobConsumer {
    @Autowired
    private ReadyQueue readyQueue;
    @Autowired
    private JobPool jobPool;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @PostConstruct
    public void init() {
        executorService.execute(() -> {
            while (true) {
                try {
                    Job job = readyQueue.getJob("");
                    job.setStatus(DelayJobEnums.RESERVED.getValue());
                    if (job != null) {
                        log.info("获取到job信息:{}", job);
                        log.info("job延时时间:{},时间差值:{}", job.getDelayTime(), System.currentTimeMillis() - job.getCurrentTime());
                        jobPool.removeDelayDelayJob(job.getId());
                    }
                } catch (Exception e) {

                }
            }
        });
    }
}
