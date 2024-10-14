package com.zxw.netty.handler;

import com.yfyy.netty.AbstractNettyServerConnector;
import com.yfyy.utils.DelayUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2023-10-30 10:13
 */
@Slf4j
public class ConnectListener implements ChannelFutureListener {
    AbstractNettyServerConnector abstractNettyConnector;

    public ConnectListener(AbstractNettyServerConnector abstractNettyConnector) {
        this.abstractNettyConnector = abstractNettyConnector;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            Throwable cause = future.cause();
            if (cause instanceof ConnectTimeoutException) {
                log.error("ass客户端连接超时 --- ip:{} --- port:{} --- exp:", abstractNettyConnector.getServerAddress(), abstractNettyConnector.getServerPort(), cause);
            } else {
                log.error("连接异常", cause);
            }
            future.channel().close();
            // fix:避免产生多个重连连接
            if (!abstractNettyConnector.getIsReConnecting().get()) {
                DelayUtils.push(() -> abstractNettyConnector.restart(), 60, TimeUnit.SECONDS);
                abstractNettyConnector.getIsReConnecting().set(true);
            }
        }
    }
}
