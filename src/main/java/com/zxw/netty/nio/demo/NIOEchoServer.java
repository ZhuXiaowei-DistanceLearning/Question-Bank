package com.zxw.netty.nio.demo;

import java.io.IOException;

 
public class NIOEchoServer {

 
    public static void main(String[] args) throws IOException {
	int port = 8080;
	if (args != null && args.length > 0) {
	    try {
		port = Integer.valueOf(args[0]);
	    } catch (NumberFormatException e) {
		// 采用默认值
	    }
	}
	EchoHandler timeServer = new EchoHandler(port);
	new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}