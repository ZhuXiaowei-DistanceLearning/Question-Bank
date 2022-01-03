package com.zxw.netty.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter  {

    private ChannelHandlerContext context; //上下文
    private String result; //返回的结果
    private String para; //客户端调用方法时，传入的参数

    //与服务端创建连接后调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道连接成功");
        context = ctx; //因为我们在其他方法会使用到 ctx
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); //唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
