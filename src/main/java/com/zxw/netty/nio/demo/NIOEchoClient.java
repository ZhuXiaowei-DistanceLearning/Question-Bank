package com.zxw.netty.nio.demo;

public class NIOEchoClient {

	public static void main(String[] args) {

		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
			}
		}
		new Thread(new NIOEchoClientHandler("127.0.0.1", port), "NIOEchoClient-001").start();
	}
}