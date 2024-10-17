package com.zxw.quotes;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.zxw.quotes.config.ServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zxw
 * @date 2024-10-15 11:08
 */
@Slf4j
public class QuotesStarter {

    static ByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
    private static final Random random = new Random();
    public static void main(String[] args) {
        startQuotesNetty();
    }

    public static void startQuotesNetty() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setEnabled(true);
        serverConfig.setName("server");
        serverConfig.setPort(9502);
        QuotesServer quotesNettyServer = new QuotesServer(serverConfig);
        quotesNettyServer.start("quotes", serverConfig.getPort());
        batchExecuteScanLoop(quotesNettyServer);
    }

    /**
     * 导出TsData
     */
    public static void batchExecuteScanLoop(QuotesServer quotesNettyServer) {
        int nThreads = Integer.parseInt(System.getProperty("test.thread.nums"));
//        int nThreads = 2;
        ExecutorService executorService = Executors.newCachedThreadPool();
        RateLimiter limiter = RateLimiter.create(50000);
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(() -> {
                log.info("启动netty-{}", Thread.currentThread());
                // 创建一个每秒最多允许 5 个请求的限流器
                while (true) {
                    List<ByteBuf> bufList = Lists.newArrayList(
                            generateMessage(700),
                            generateMessage(9988),
                            generateMessage(9626),
                            generateMessage(3690)
                    );
                    for (ByteBuf byteBuf : bufList) {
                        limiter.acquire();
                        quotesNettyServer.sendAllClientMessage(byteBuf);
                    }
                }
            });
        }
    }

    public static ByteBuf buildStatisTsData(int assetId) {
        ByteBuf buffer = allocator.directBuffer(52).order(ByteOrder.LITTLE_ENDIAN);
        buffer.writeShort((short) 52);
        buffer.writeShort((short) 60);
        buffer.writeInt(assetId); // uint32
        buffer.writeLong(ThreadLocalRandom.current().nextInt(2, 500)); // uint64
        buffer.writeLong(ThreadLocalRandom.current().nextInt(2, 500)); // int64, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // uint32
        buffer.writeLong(ThreadLocalRandom.current().nextInt(2, 500)); // int64, 3 implied decimal places
        return buffer;
    }

    public static ByteBuf generateMessage(int assetId) {
        int randomValue = random.nextInt(10); // 生成 0-9 的随机数
        if (randomValue < 8) {
            return buildOrderData(assetId); // 概率为 8/10
        } else if (randomValue == 8) {
            return buildTickerMessage(assetId); // 概率为 1/10
        } else {
            return buildStatisTsData(assetId); // 概率为 1/10
        }
    }

    public static ByteBuf buildTickerMessage(int assetId) {
        ByteBuf buffer = allocator.directBuffer(32).order(ByteOrder.LITTLE_ENDIAN);
        // 设置小端模式读取整数
        buffer.writeShort((short) 32);
        buffer.writeShort((short) 50);
        buffer.writeInt(assetId); // uint32
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // uint32
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // int32
        buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500)); // uint32
        buffer.writeShort((short) 0); // int16
        buffer.writeBytes(new byte[2]);
        buffer.writeLong(System.nanoTime()); // uint64
        return buffer;
    }

    public static ByteBuf buildOrderData(int assetId) {
        ByteBuf buffer = allocator.directBuffer(60).order(ByteOrder.LITTLE_ENDIAN);
        // 12 + 48
        buffer.writeShort((short) 60);
        buffer.writeShort((short) 53);
        buffer.writeInt(assetId); // uint32
        buffer.writeBytes(new byte[3]);
        buffer.writeByte(2);
        // 24*2
        for (int i = 0; i < 2; i++) {
            buffer.writeLong(ThreadLocalRandom.current().nextInt(2, 500)); // uint64
            buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500));
            buffer.writeInt(ThreadLocalRandom.current().nextInt(2, 500));
            buffer.writeShort((short) 1);
            buffer.writeByte(1);
            buffer.writeByte(1);
            buffer.writeBytes(new byte[4]);
        }
        return buffer;
    }
}
