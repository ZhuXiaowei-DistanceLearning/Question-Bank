package com.zxw.netty;

import cn.hutool.core.date.DateUtil;
import com.yfyy.netty.handler.ConnectListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxw
 * @date 2024-05-22 16:35
 */
@Slf4j
@Getter
public abstract class AbstractNettyServerConnector {

    EventLoopGroup bossGroup;

    EventLoopGroup workGroup;

    ServerBootstrap bootstrap;

    Channel channel;

    /**
     * 服务地址
     */
    String serverAddress;

    String name;

    LocalTime openTime;
    LocalTime closeTime;

    /**
     * 服务端口号
     */
    int serverPort;

    /**
     * 线程处理数量
     */
    int threadNum;

    /**
     * 重连试次数
     */
    AtomicInteger reConnectNum = new AtomicInteger(0);

    /**
     * 上次重连时间
     */
    long connectTime = System.currentTimeMillis();

    /**
     * 连接超时，10秒
     */
    private static final int CONNECT_TIMEOUT_MILLIS = 10 * 1000;

    AtomicBoolean isReConnecting = new AtomicBoolean(false);

    AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * 启动netty
     */
    public synchronized void start(String name, int serverPort) {
        this.serverPort = serverPort;
        this.name = name;
        setOpenMarketTime(null, null);
        log.info("启动server服务器 name:[{}] --- 端口:[{}]", name, serverPort);
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
                .handler(getServerChannelHandler(this))
                .childHandler(getChannelHandler(this));
        registerChildOption(bootstrap);
        connect();
    }

    /**
     * 自定义配置
     */
    public void registerChildOption(ServerBootstrap bootstrap) {

    }

    /**
     * 关闭netty
     */
    public synchronized void stop() {
        try {
            log.info("name：{} --- port：{} 关闭netty服务端", getName(), getServerPort());
            isRunning.set(false);
            if (channel.isActive()) {
                channel.close();
            }
            if (!workGroup.isTerminated()) {
                workGroup.shutdownGracefully();
            }
            if (!bossGroup.isTerminated()) {
                bossGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            log.error("netty关闭异常", e);
        }
    }

    /**
     * 获取netty server处理器
     */
    public ChannelInitializer getServerChannelHandler(AbstractNettyServerConnector connector) {
        return new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                addServerPipeline(pipeline);
            }
        };
    }

    /**
     * 获取netty处理器
     */
    public ChannelInitializer getChannelHandler(AbstractNettyServerConnector connector) {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
//                pipeline.addLast(new ReadTimeoutHandler(15, TimeUnit.SECONDS));
                addPipeline(pipeline);
            }
        };
    }

    private void printPipeline(ChannelPipeline pipeline) {
        System.out.println("Current ChannelPipeline:");
        pipeline.toMap().forEach((name, handler) ->
                System.out.println("Name: " + name + ", Handler: " + handler.getClass().getSimpleName())
        );
    }

    /**
     * server子类自定义处理器
     */
    public void addServerPipeline(ChannelPipeline pipeline) {
        // continue
    }

    /**
     * 子类自定义处理器
     */
    public void addPipeline(ChannelPipeline pipeline) {
        // continue
    }

    /**
     * 重启netty连接
     */
    public synchronized void restart() {
        getIsReConnecting().set(false);
        if (closeTime != null && LocalTime.now().isAfter(closeTime)) {
            log.info("超过收盘时间,不进行重启");
            stop();
            return;
        }
        reConnectNum.incrementAndGet();
        long lastConnectTime = connectTime;
        connectTime = System.currentTimeMillis();
        if (reConnectNum.get() > 3) {
            String oldIp = serverAddress;
            this.changeStartIp();
            log.error("重试次数超过限制,开始切换ip,当前ip:{},切换后ip:{}", oldIp, serverAddress);
        }
        log.error("连接断开,--- 地址:[{}] --- 端口:[{}] --- 开始重连 --- 重连次数:{} --- 上次重连时间:{}", serverAddress, serverPort, reConnectNum, DateUtil.formatDateTime(new Date(lastConnectTime)));
        if (bossGroup.isTerminated() && workGroup.isTerminated()) {
            this.start(this.name, this.serverPort);
        } else {
            connect();
        }
    }

    /**
     * 发起连接
     */
    public void connect() {
        ChannelFuture connect = bootstrap.bind(serverPort);
        connect.addListener(new ConnectListener(this));
        try {
            connect.get();
        } catch (Exception cause) {
            log.error("服务端连接超时 --- port:{} --- exp:", this.getServerPort(), cause);
        }
        isRunning.set(true);
        channel = connect.channel();
    }

    /**
     * 重置指标数据
     */
    public void resetMetrics() {
        reConnectNum.set(0);
    }

    public boolean isRunning(){
        return this.isRunning.get();
    }

    /**
     * 更换启动ip
     */
    public void changeStartIp() {
        this.resetMetrics();
    }

    /**
     * 设置开市区间
     */
    public void setOpenMarketTime(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
