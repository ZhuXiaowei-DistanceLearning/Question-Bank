package com.zxw.netty.demo.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class AbsIntegerEncoderTest {
    /**
     * 将 4 字节的负整数写到一个新的 ByteBuf 中。
     * 创建一个 EmbeddedChannel，并为它分配一个 AbsIntegerEncoder。
     * 调用 EmbeddedChannel 上的 writeOutbound()方法来写入该 ByteBuf。
     * 标记该 Channel 为已完成状态。
     * 从 EmbeddedChannel 的出站端读取所有的整数，并验证是否只产生了绝对值。
     */
    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel(
                new AbsIntegerEncoder());
        // 写入 ByteBuf，并断言调用 readOutbound()方法将会产生数据
        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());
// read bytes
        for (int i = 1; i < 10; i++) {
            // 读取所产生的消息，并断言它们包含了对应的绝对值
            Assert.assertEquals(i, java.util.Optional.ofNullable(channel.readOutbound()));
        }
        Assert.assertNull(channel.readOutbound());
    }
}