package com.zxw.netty.demo.handler;

import com.zxw.netty.demo.encode.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2021/12/31 14:55
 */
@Slf4j
public class ClientMessageInboundHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        log.info("客户端接受到消息：{}", msg.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        //使用客户端发送10条数据，"今天天气冷，吃火锅" 编号
        for (int i = 0; i < 3; i++) {
            String message = "Server" + i;
            byte[] content = message.getBytes(CharsetUtil.UTF_8);
            int length = content.length;

            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

}
