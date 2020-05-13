package com.zxw.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zxw
 * @date 2020-05-12 14:14:52
 * @Description
 */
public class NIOServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();
            socketChannel.bind(new InetSocketAddress(10010));
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                int select = selector.select();
                if(select > 0){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
