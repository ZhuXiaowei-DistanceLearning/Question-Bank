package com.zxw.quotes;

import com.zxw.utils.DelayUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2023-10-30 10:33
 */
@Slf4j
public class ReconnectHandler extends ChannelInboundHandlerAdapter {

    AbstractNettyServerConnector connector;

    public ReconnectHandler(AbstractNettyServerConnector connector) {
        this.connector = connector;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ip:{}, port:{} --- channel通道激活", connector.getServerAddress(), connector.getServerPort());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
//        log.error("ip:{}, port:{} --- channel连接断开,开始重新连接", connector.getServerAddress(), connector.getServerPort());
        // Reconnect
//        DelayUtils.push(() -> connector.restart(), 60, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ip:{}, port:{} --- 通道异常:", connector.getServerAddress(), connector.getServerPort(), cause);
        if (cause instanceof ReadTimeoutException) {
            log.error("超过15s没有获取到ass心跳消息");
            ChannelFuture close = ctx.close();
            if (close.isDone() && close.isSuccess()) {
                connector.restart();
            }
        } else {
            if (!connector.getIsReConnecting().get()) {
                DelayUtils.push(() -> connector.restart(), 60, TimeUnit.SECONDS);
                connector.getIsReConnecting().set(true);
            }
        }
    }
}
