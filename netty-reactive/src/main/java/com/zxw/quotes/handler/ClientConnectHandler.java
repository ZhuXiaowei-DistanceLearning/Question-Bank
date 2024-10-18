package com.zxw.quotes.handler;

import com.google.common.collect.Lists;
import com.zxw.quotes.QuotesServer;
import com.zxw.quotes.client.ClientInfo;
import com.zxw.quotes.client.ClientInfoManager;
import com.zxw.quotes.utils.StockBufferUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author zxw
 * @date 2024-05-23 10:10
 */
@Slf4j
@AllArgsConstructor
public class ClientConnectHandler extends ChannelInboundHandlerAdapter {

    ClientInfoManager clientInfoManager;

    QuotesServer aliServer;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        int nThreads = Integer.parseInt(System.getProperty("test.thread.nums"));
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 获取客户端的远程地址
        Channel channel = ctx.channel();
        InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        String clientName = clientIp + ":" + remoteAddress.getPort();
        log.info("服务端:[{}-{}] --- 客户端:[{}] --- 连接成功", aliServer.getName(), aliServer.getServerPort(), clientName);
        // 将客户端信息存储到 Map 中
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setIp(clientIp);
        clientInfo.setPort(remoteAddress.getPort());
        clientInfo.setName(clientName);
        clientInfo.setCtx(ctx);
        clientInfoManager.addClient(clientInfo);
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(() -> {
                while (true) {
                    if (channel.isActive() && channel.isWritable()) {
                        List<ByteBuf> bufList = Lists.newArrayList(
                                StockBufferUtils.generateMessage(700),
                                StockBufferUtils.generateMessage(9988),
                                StockBufferUtils.generateMessage(9626),
                                StockBufferUtils.generateMessage(3690)
                        );
                        bufList.forEach(e -> ctx.writeAndFlush(e));
                    }
                }
            });
        }
        super.channelActive(ctx);
    }

    private static void send(ChannelHandlerContext ctx, int nThreads, ExecutorService executorService) {
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(() -> {
                while (true) {
                    if (ctx.channel().isWritable()) {
                        List<ByteBuf> bufList = Lists.newArrayList(
                                StockBufferUtils.generateMessage(700),
                                StockBufferUtils.generateMessage(9988),
                                StockBufferUtils.generateMessage(9626),
                                StockBufferUtils.generateMessage(3690)
                        );
                        bufList.forEach(e -> ctx.channel().writeAndFlush(e));
                    }
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        String clientName = clientIp + ":" + remoteAddress.getPort();
        // ip名单中的不处理
        log.info("服务端:[{}-{}] --- 客户端:[{}] --- 连接断开", aliServer.getName(), aliServer.getServerPort(), clientName);
        clientInfoManager.removeClient(clientName);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        String clientName = clientIp + ":" + remoteAddress.getPort();
        // ip名单中的不处理
        log.error("服务端:[{}-{}] --- client:[{}]发生异常", aliServer.getName(), aliServer.getServerPort(), clientName, cause);
    }
}
