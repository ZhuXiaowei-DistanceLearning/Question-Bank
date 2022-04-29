package com.zxw.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxw.constants.RedisKeyConsts;
import com.zxw.delay.entity.DelayJob;
import com.zxw.delay.entity.Job;
import com.zxw.delay.entity.JobPool;
import com.zxw.delay.event.DelayBucket;
import com.zxw.exception.BusinessException;
import com.zxw.exception.ExpMsgConsts;
import com.zxw.vo.JobSaveVo;
import com.zxw.web.service.DelayJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

/**
 * @author zxw
 * @date 2021/10/25 15:45
 */
//@Service
public class DelayJobServiceImpl implements DelayJobService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JobPool jobPool;

    @Autowired
    private DelayBucket delayBucket;

    /**
     * 其核心设计思路：
     * <p>
     * 将延迟的消息任务通过 hash 算法路由至不同的 Redis Key 上，这样做有两大好处：
     * a. 避免了当一个 KEY 在存储了较多的延时消息后，入队操作以及查询操作速度变慢的问题（两个操作的时间复杂度均为O(logN)）。
     * b. 系统具有了更好的横向可扩展性，当数据量激增时，我们可以通过增加 Redis Key 的数量来快速的扩展整个系统，来抗住数据量的增长。
     * 每个 Redis Key 都对应建立一个处理进程，称为 Event 进程，通过上述步骤 2 中所述的 ZRANGEBYSCORE 方法轮询 Key，查询是否有待处理的延迟消息。
     * 所有的 Event 进程只负责分发消息，具体的业务逻辑通过一个额外的消息队列异步处理，这么做的好处也是显而易见的：
     * a. 一方面，Event 进程只负责分发消息，那么其处理消息的速度就会非常快，就不太会出现因为业务逻辑复杂而导致消息堆积的情况。
     * b. 另一方面，采用一个额外的消息队列后，消息处理的可扩展性也会更好，我们可以通过增加消费者进程数量来扩展整个系统的消息处理能力。
     * Event 进程采用 Zookeeper 选主单进程部署的方式，避免 Event 进程宕机后，Redis Key 中消息堆积的情况。一旦 Zookeeper 的 leader 主机宕机，Zookeeper 会自动选择新的 leader 主机来处理 Redis Key 中的消息。
     * 从上述的讨论中我们可以看到，通过 Redis Zset 实现延迟队列是一种理解起来较为直观，可以快速落地的方案。并且我们可以依赖 Redis 自身的持久化来实现持久化，使用 Redis 集群来支持高并发和高可用，是一种不错的延迟队列的实现方案。
     * // -- 有赞
     * 用户对某个商品下单，系统创建订单成功，同时往延迟队列里put一个job。job结构为：{‘topic':'orderclose’, ‘id':'ordercloseorderNoXXX’, ‘delay’:1800 ,’TTR':60 , ‘body':’XXXXXXX’}
     * 延迟队列收到该job后，先往job pool中存入job信息，然后根据delay计算出绝对执行时间，并以轮询(round-robbin)的方式将job id放入某个bucket。
     * timer每时每刻都在轮询各个bucket，当1800秒（30分钟）过后，检查到上面的job的执行时间到了，取得job id从job pool中获取元信息。如果这时该job处于deleted状态，则pass，继续做轮询；如果job处于非deleted状态，首先再次确认元信息中delay是否大于等于当前时间，如果满足则根据topic将job id放入对应的ready queue，然后从bucket中移除；如果不满足则重新计算delay时间，再次放入bucket，并将之前的job id从bucket中移除。
     * 消费端轮询对应的topic的ready queue（这里仍然要判断该job的合理性），获取job后做自己的业务逻辑。与此同时，服务端将已经被消费端获取的job按照其设定的TTR，重新计算执行时间，并将其放入bucket。
     * 消费端处理完业务后向服务端响应finish，服务端根据job id删除对应的元信息。
     *
     * @param job 任务
     */
    @Override
    public void put(Job job) {
        String jobId = UUID.randomUUID().toString();
        job.setId(jobId);
//        DefaultRedisScript redisScript =new DefaultRedisScript<>();
//        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/addJob.lua")));
//        redisTemplate.execute(redisScript,keys,RedisKeyUtil.getTopicId(topic, arg.getId()),arg,runTimeMillis);
        redisTemplate.opsForHash().put(RedisKeyConsts.JOB_POOL, jobId, JSON.toJSONString(job));
        redisTemplate.opsForZSet().addIfAbsent(RedisKeyConsts.DELAY_BUCKET, jobId, System.currentTimeMillis());
    }

    @Override
    public void pop(Job job) {

    }

    @Override
    public void finish(Job job) {

    }

    @Override
    public void delete(Job job) {

    }

    @Override
    public void delayHandler(String message) {
        String id = UUID.randomUUID().toString();
        JobSaveVo jobVo = JSON.parseObject(message, JobSaveVo.class);
        JSONObject body = JSON.parseObject(message);
        Job job = new Job();
        job.setBody(jobVo.getBody());
        job.setTtrTime(jobVo.getTtrTime());
        job.setRetryCount(3);
        job.setTopic(jobVo.getTopic());
        job.setDelayTime(jobVo.getDelayTime());
        job.setId(id);
        job.setCurrentTime(System.currentTimeMillis());
        String command = body.getString("command");
        switch (command) {
            case "put":
                jobPool.addDelayJob(job);
                DelayJob delayJob = new DelayJob(job);
                delayBucket.addDelayJob(delayJob);
                break;
            case "pop":
                jobPool.removeDelayDelayJob(job.getId());
                break;
//            case "finish":
//                jobPool.finish(job);
//                break;
//            case "delete":
//                jobPool.delete(job);
//                break;
            default:
                throw new BusinessException(ExpMsgConsts.COMMON_ERROR, "无效操作类型" + command);
        }
    }
}
