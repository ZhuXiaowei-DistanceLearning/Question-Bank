package com.zxw.delay.consumer;

import com.zxw.delay.entity.Job;
import com.zxw.delay.entity.ReadyQueue;
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

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @PostConstruct
    public void init() {
        executorService.execute(() -> {
            while (true) {
                Job job = readyQueue.getJob("");
                log.info("获取到job信息:{}", job);
                log.info("job延时时间:{},时间差值:{}", job.getDelayTime(), System.currentTimeMillis() - job.getCurrentTime());
            }
        });
    }
}
