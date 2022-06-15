package com.zxw.netty.demo.handler.server;

import com.zxw.netty.demo.coder.define.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author zxw
 * @date 2021/12/31 14:55
 */
@Slf4j
public class ServerMessageInboundHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    int count = 0;

    //接收的Handler继承了SimpleChannelInboundHandler，以MessageProtocol的类型接受消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务器第 " + (++count) + " 次接收到信息如下：");
        System.out.println("长度：" + len);
        System.out.println("内容：" + new String(content, CharsetUtil.UTF_8));

        //回复消息
        String response = UUID.randomUUID().toString();
        int length = response.getBytes(CharsetUtil.UTF_8).length;
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(response.getBytes());
        ctx.writeAndFlush(messageProtocol);
    }
}
