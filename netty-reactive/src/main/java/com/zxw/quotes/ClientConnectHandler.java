package com.zxw.quotes;

import com.zxw.quotes.client.ClientInfo;
import com.zxw.quotes.client.ClientInfoManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

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
        // 获取客户端的远程地址
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
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
        super.channelActive(ctx);
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
