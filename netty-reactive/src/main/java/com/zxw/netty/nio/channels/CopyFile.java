package com.zxw.netty.nio.channels;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * FileChannel：文件数据传输通道
 * DatagramChannel：UDP 数据传输通道
 * SocketChannel：客户端 TCP 数据传输通道
 * ServerSocketChannel：服务器端 TCP 数据传输通道
 */
public class CopyFile {
    static public void main(String args[]) throws Exception {
        String infile = "src/main/resources/CopyFile.java";
        String outfile = "src/main/resources/CopyFile.java.copy";


        // 从流中获取通道
        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            // 读入之前要清空
            buffer.clear();

            // position自动前进
            int r = fcin.read(buffer);

            if (r == -1) {
                break;
            }

            // position = 0; limit=读到的字节数
            buffer.flip();

            // 从 buffer 中读
            fcout.write(buffer);
        }
    }
}
