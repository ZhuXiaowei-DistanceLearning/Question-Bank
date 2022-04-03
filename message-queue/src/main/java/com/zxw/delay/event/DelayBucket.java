package com.zxw.delay.event;

import com.alibaba.fastjson.JSON;
import com.zxw.constants.RedisKeyConsts;
import com.zxw.delay.entity.DelayJob;
import com.zxw.delay.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class DelayBucket {
    @Autowired
    private RedisTemplate redisTemplate;
    private static AtomicInteger index = new AtomicInteger(0);
    private int bucketsSize = 3;
    private List<String> bucketNames = new ArrayList<>();

    @Bean
    public List<String> createBuckets() {
        for (int i = 0; i < bucketsSize; i++) {
            bucketNames.add(RedisKeyConsts.DELAY_BUCKET + "_" + i);
        }
        return bucketNames;
    }

    /**
     * 获得桶的名称
     *
     * @return
     */
    private String getThisBucketName() {
        int thisIndex = index.addAndGet(1);
        int i1 = thisIndex % bucketsSize;
        return bucketNames.get(i1);
    }

    /**
     * 获得桶集合
     *
     * @param bucketName
     * @return
     */
    private BoundZSetOperations getBucket(String bucketName) {
        return redisTemplate.boundZSetOps(bucketName);
    }

    /**
     * 放入延时任务
     *
     * @param job
     */
    public void addDelayJob(DelayJob job) {
        log.info("添加延迟任务:{}", JSON.toJSONString(job));
        String bucketName = getThisBucketName();
        redisTemplate.opsForZSet().add(bucketName, job.getJodId(), job.getDelayDate());
//        String thisBucketName = getThisBucketName();
//        BoundZSetOperations bucket = getBucket(thisBucketName);
//        bucket.add(job, job.getDelayDate());
    }

    /**
     * 获得最新的延期任务
     *
     * @return
     */
    public Job getFirstDelayTime(Integer index) {
        String name = bucketNames.get(index);
        BoundZSetOperations bucket = getBucket(name);
        Set<ZSetOperations.TypedTuple> set = bucket.rangeWithScores(0, 1);
        if (CollectionUtils.isEmpty(set)) {
            return null;
        }
        ZSetOperations.TypedTuple typedTuple = (ZSetOperations.TypedTuple) set.toArray()[0];
        Object value = typedTuple.getValue();
        if (value instanceof Job) {
            return (Job) value;
        }
        return null;
    }

    /**
     * 移除延时任务
     *
     * @param index
     * @param delayJob
     */
    public void removeDelayTime(Integer index, DelayJob delayJob) {
        String name = bucketNames.get(index);
        BoundZSetOperations bucket = getBucket(name);
        bucket.remove(delayJob);
    }
}