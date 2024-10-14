package com.zxw.quotes;

import com.zxw.quotes.client.ClientInfo;
import com.zxw.quotes.client.ClientInfoManager;
import com.zxw.quotes.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.WriteBufferWaterMark;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author zxw
 * @date 2024-05-22 16:55
 */
@Slf4j
@Getter
public class QuotesServer extends AbstractNettyServerConnector {

    ServerConfig serverConfig;

    ClientInfoManager clientInfoManager = new ClientInfoManager();

    @Override
    public void registerChildOption(ServerBootstrap bootstrap) {
        bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024 * 1024, 64 * 1024 * 1024));
    }

    @Override
    public void addServerPipeline(ChannelPipeline pipeline) {
//        pipeline.addLast(new IdleStateHandler(10, 0, 0));
    }

    @Override
    public void addPipeline(ChannelPipeline pipeline) {
//        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(new MonitorTrafficShapingHandler(getName(), 0, 0, 300000));
        pipeline.addLast(new ClientSendHandler());
        pipeline.addLast(new ClientConnectHandler(clientInfoManager, this));
    }

    public QuotesServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void sendAllClientMessage(ByteBuf msg) {
        Map<String, ClientInfo> clientInfoMap = clientInfoManager.findAll();
        // 获取订阅的客户端列表
        clientInfoMap.forEach((k, v) -> {
            try {
                msg.retain();
                v.sendMessage(msg);
            } catch (Exception exp) {
                log.error("推送消息至客户端异常,", exp);
            }
        });
        msg.release();
    }
}
