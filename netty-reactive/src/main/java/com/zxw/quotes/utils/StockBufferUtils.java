package com.zxw.quotes.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.nio.ByteOrder;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zxw
 * @date 2024-10-18 10:42
 */
public class StockBufferUtils {

    private static final Random random = new Random();
    private static ByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

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
