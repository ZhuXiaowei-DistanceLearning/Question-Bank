package com.zxw.netty.demo.coder;

import com.zxw.netty.demo.coder.charc.ByteToCharDecoder;
import com.zxw.netty.demo.coder.charc.CharToByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 正如我们前面所提到的，结合一个解码器和编码器可能会对可重用性造成影响。但是，有一
 * 种方法既能够避免这种惩罚，又不会牺牲将一个解码器和一个编码器作为一个单独的单元部署所
 * 带来的便利性。CombinedChannelDuplexHandler 提供了这个解决方案，其声明为：
 * public class CombinedChannelDuplexHandler
 * <I extends ChannelInboundHandler,
 * O extends ChannelOutboundHandler>
 * 这个类充当了 ChannelInboundHandler 和 ChannelOutboundHandler（该类的类型
 * 参数 I 和 O）的容器。通过提供分别继承了解码器类和编码器类的类型
 */
public class CombinedByteCharCodec extends
        CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}