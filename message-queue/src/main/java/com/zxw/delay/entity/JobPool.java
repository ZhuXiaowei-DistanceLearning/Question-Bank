package com.zxw.delay.entity;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JobPool {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private String NAME = "DelayJob.pool";
    private Map<String, Job> jobMap = new HashMap<>();

    @PostConstruct
    public void init() {
        List<Object> values = redisTemplate.opsForHash().values(NAME);
        if (CollectionUtils.isNotEmpty(values)) {
            values.forEach(e -> {
                Job job = JSON.parseObject(e.toString(), Job.class);
                jobMap.put(job.getId(), job);
            });
        }
    }

    /**
     * 添加任务
     *
     * @param job
     */
    public void addDelayJob(Job job) {
        log.info("任务池添加任务：{}", JSON.toJSONString(job));
        redisTemplate.opsForHash().put(NAME, job.getId(), JSON.toJSONString(job));
        jobMap.put(job.getId(), job);
        return;
    }

    public void setJobStatus(String jobId, String status) {
        Job job = jobMap.get(jobId);
        if (job != null) {
            job.setStatus(status);
        }
    }

    /**
     * 获得任务
     *
     * @param DelayJobId
     * @return
     */
    public Job getDelayJob(String DelayJobId) {
        return jobMap.get(DelayJobId);
    }

    /**
     * 移除任务
     *
     * @param DelayJobId
     */
    public void removeDelayDelayJob(String DelayJobId) {
        log.info("任务池移除任务：{}", DelayJobId);
        // 移除任务
        redisTemplate.opsForHash().delete(NAME, DelayJobId);
    }
}