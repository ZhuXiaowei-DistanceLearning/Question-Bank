package com.zxw.web;

import com.zxw.apiversion.APIVersion;
import com.zxw.cache.redis.LockUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2022/6/13 14:33
 */
@RestController
@RequestMapping("/redis")
@APIVersion("v4")
@Slf4j
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LockUtils lockUtils;

    private AtomicInteger atomicInteger = new AtomicInteger();

    ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping("/test")
//    @Cacheable({"hot"})
    public String test() {
        executorService.execute(() -> {
            lockUtils.lock("test", () -> {
                log.info("线程1");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.execute(() -> {
            lockUtils.lock("test", () -> {
                log.info("线程2");
            });
        });
        return "123";
    }

    @SneakyThrows
//    @PostConstruct
    public void wrongInit() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //每隔30秒全量更新一次缓存
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            IntStream.rangeClosed(1, 1000).forEach(i -> {
                String data = getCityFromDb(i);
                //模拟更新缓存需要一定的时间
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                }
                if (!StringUtils.isEmpty(data)) {
                    //缓存永不过期，被动更新
                    stringRedisTemplate.opsForValue().set("city" + i, data);
                }
            });
            log.info("Cache update finished");
            //启动程序的时候需要等待首次更新缓存完成
            countDownLatch.countDown();
        }, 0, 30, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
        countDownLatch.await();
    }

    @GetMapping("city")
    public String city() {
        //随机查询一个城市
        int id = ThreadLocalRandom.current().nextInt(1000) + 1;
        String key = "city" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (data == null) {
            //回源到数据库查询
            data = getCityFromDb(id);
            if (!StringUtils.isEmpty(data))
                //缓存30秒过期
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
        }
        return data;
    }

    private String getCityFromDb(int cityId) {
        //模拟查询数据库，查一次增加计数器加一
        atomicInteger.incrementAndGet();
        return "citydata" + System.currentTimeMillis();
    }
}
