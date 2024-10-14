package com.zxw.quotes;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 流量整形器
 *
 * @author zxw
 * @date 2023-09-28 10:53
 */
@Slf4j
public class MonitorTrafficShapingHandler extends ChannelTrafficShapingHandler {

    Channel channel;

    String name;

    String serviceTypeCode;

    Long count = 0L;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (channel == null) {
            channel = ctx.channel();
        }
        count++;
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count++;
        super.channelRead(ctx, msg);
    }

    @Override
    protected void doAccounting(TrafficCounter counter) {
        try {
            if (channel == null) {
                return;
            }
            // 打印其他指标数据，如读写字节数，读写速率等
            log.info("*********流量监控日志*********");
            BigDecimal writeSize = BigDecimalCalUtils.div(BigDecimal.valueOf(counter.lastWrittenBytes()), 2, BigDecimal.valueOf(1024), BigDecimal.valueOf(1024));
            BigDecimal readSize = BigDecimalCalUtils.div(BigDecimal.valueOf(counter.lastReadBytes()), 2, BigDecimal.valueOf(1024), BigDecimal.valueOf(1024));
            BigDecimal writeThroughput = BigDecimal.valueOf(counter.lastWriteThroughput()).divide(BigDecimal.valueOf(1000), 2, RoundingMode.DOWN);
            BigDecimal readThroughput = BigDecimal.valueOf(counter.lastReadThroughput()).divide(BigDecimal.valueOf(1000), 2, RoundingMode.DOWN);
            BigDecimal totalReadSize = BigDecimalCalUtils.div(BigDecimal.valueOf(counter.cumulativeReadBytes()), 2, BigDecimal.valueOf(1024), BigDecimal.valueOf(1024));
            BigDecimal totalWriteSize = BigDecimalCalUtils.div(BigDecimal.valueOf(counter.cumulativeWrittenBytes()), 2, BigDecimal.valueOf(1024), BigDecimal.valueOf(1024));
            log.info("\nname:[{}] --- serviceType:[{}] --- channel:[{}]\n 消息数量:[{}]\nduring write size --- {} MB\nduring read size --- {} MB\nduring write throughput --- {} KB/s\nduring read throughput  --- {} KB/s\ntotal read size: {} MB\ntotal write size: {} MB",
                    name, serviceTypeCode, channel, count, writeSize, readSize, writeThroughput, readThroughput, totalReadSize, totalWriteSize);
            count = 0L;
        } catch (Exception e) {
            log.error("流量检测异常：", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public MonitorTrafficShapingHandler(String name, String serviceTypeCode, long writeLimit, long readLimit, long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
        this.name = name;
        this.serviceTypeCode = serviceTypeCode;
    }

    public MonitorTrafficShapingHandler(String name, long writeLimit, long readLimit, long checkInterval) {
       this(name, "", writeLimit, readLimit, checkInterval);
    }

}
