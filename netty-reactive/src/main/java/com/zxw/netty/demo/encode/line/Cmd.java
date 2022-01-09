package com.zxw.netty.demo.encode.line;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public final class Cmd {
    private ByteBuf name;
    private ByteBuf args;

}