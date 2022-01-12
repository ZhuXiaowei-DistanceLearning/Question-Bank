package com.zxw.netty.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoHandler implements Runnable {

	private Selector selector;
	private ServerSocketChannel servChannel;
	private volatile boolean stop;
	private int num = 0;

	public EchoHandler(int port) {
		try {
			// 打开Selector来处理Channel
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			// socket函数和bind函数
			servChannel.socket().bind(new InetSocketAddress(port), 1024);
			// 将ServerSocket注册到Selector以接受连接
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("服务器在端口[" + port + "]等待客户请求......");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				// 阻塞等待需要发生的事件
				// 等待需要处理的新事件；阻塞 将一直持续到下一个传入事件
				// 阻塞直到有一个socket接收到数据
				selector.select(1000);
				// 获取所有接收事件的SelectionKey实例
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null)
								key.channel().close();
						}
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		// 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if (selector != null)
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void handleInput(SelectionKey key) throws IOException {

		if (key.isValid()) {
			// 处理新接入的请求消息
			// 检查事件是否是一个新的已经就绪可以被接受的连接
			if (key.isAcceptable()) {
				// 处理accept事件
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel socketChannel = ssc.accept(); // Non blocking, never null
				socketChannel.configureBlocking(false);
				// 接受客户端，并将它注册到选择器
				SelectionKey sk = socketChannel.register(selector, SelectionKey.OP_READ);

				sk.attach(num++);
			}
			if (key.isReadable()) {
				// 读取数据
				// 处理read事件
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("来自客户端[" + key.attachment() + "]的输入： [" + body.trim() + "]！");

					if (body.trim().equals("Quit")) {
						System.out.println("关闭与客户端[" + key.attachment() + "]......");
						key.cancel();
						sc.close();
					} else {
						String response = "来自服务器端的响应：" + body;
						doWrite(sc, response);
					}

				} else if (readBytes < 0) {
					// 对端链路关闭
					key.cancel();
					sc.close();
				} else {

				}
			}
		}
	}

	/**
	 * 处理写事件
	 * @param channel
	 * @param response
	 * @throws IOException
	 */
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}
}
