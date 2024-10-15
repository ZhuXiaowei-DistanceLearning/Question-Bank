package com.zxw.quotes;

import com.google.common.collect.Lists;
import com.zxw.quotes.client.ClientInfo;
import com.zxw.quotes.client.ClientInfoManager;
import com.zxw.quotes.config.ServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zxw
 * @date 2024-10-15 11:08
 */
@Slf4j
public class QuotesStarter {
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

    public static void batchExecuteScanLoop(QuotesServer quotesNettyServer) {
        DefaultEventLoopGroup eventExecutors2 = new DefaultEventLoopGroup(300);
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            futureList.add(eventExecutors2.submit(() -> {
                log.info("启动netty-{}", Thread.currentThread());
                while (true) {
                    ClientInfoManager clientInfoManager = quotesNettyServer.getClientInfoManager();
                    Map<String, ClientInfo> all = clientInfoManager.findAll();
                    List<ByteBuf> bufList = Lists.newArrayList(
                            buildStatisTsData(700),
                            buildOrderData(700),
                            buildStatisTsData(9988),
                            buildTickerMessage(9988),
                            buildOrderData(9988),
                            buildStatisTsData(9626),
                            buildTickerMessage(9626),
                            buildOrderData(9626),
                            buildTickerMessage(700)
                    );
//
                    all.forEach((k, v) -> {
                        bufList.forEach(v::sendMessage);
                    });
                }
            }));
        }
    }

    public static ByteBuf buildStatisTsData(int assetId) {
        ByteBuf buffer = Unpooled.buffer(52).order(ByteOrder.LITTLE_ENDIAN);
        buffer.writeShort((short) 52);
        buffer.writeShort((short) 60);
        buffer.writeInt(assetId); // uint32
        buffer.writeLong(ThreadLocalRandom.current().nextInt()); // uint64
        buffer.writeLong(ThreadLocalRandom.current().nextInt()); // int64, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // int32, 3 implied decimal places
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // uint32
        buffer.writeLong(ThreadLocalRandom.current().nextInt()); // int64, 3 implied decimal places
        return buffer;
    }

    public static ByteBuf buildTickerMessage(int assetId) {
        ByteBuf buffer = Unpooled.buffer(32).order(ByteOrder.LITTLE_ENDIAN);
        // 设置小端模式读取整数
        buffer.writeShort((short) 32);
        buffer.writeShort((short) 50);
        buffer.writeInt(assetId); // uint32
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // uint32
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // int32
        buffer.writeInt(ThreadLocalRandom.current().nextInt()); // uint32
        buffer.writeShort((short) 0); // int16
        buffer.writeBytes(new byte[2]);
        buffer.writeLong(System.nanoTime()); // uint64
        return buffer;
    }

    public static ByteBuf buildOrderData(int assetId) {
        ByteBuf buffer = Unpooled.buffer(132).order(ByteOrder.LITTLE_ENDIAN);
        // 12 + 120
        buffer.writeShort((short) 132);
        buffer.writeShort((short) 53);
        buffer.writeInt(assetId); // uint32
        buffer.writeBytes(new byte[3]);
        buffer.writeByte(5);
        // 24*5
        for (int i = 0; i < 5; i++) {
            buffer.writeLong(ThreadLocalRandom.current().nextInt()); // uint64
            buffer.writeInt(ThreadLocalRandom.current().nextInt());
            buffer.writeInt(ThreadLocalRandom.current().nextInt());
            buffer.writeShort((short) ThreadLocalRandom.current().nextInt());
            buffer.writeByte(ThreadLocalRandom.current().nextInt());
            buffer.writeByte(ThreadLocalRandom.current().nextInt());
            buffer.writeBytes(new byte[4]);
        }
        return buffer;
    }
}
