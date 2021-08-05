package com.zxw.netty.nio.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOEchoClientHandler implements Runnable {

	private String host;
	private int port;

	private Selector selector;
	private SocketChannel socketChannel;
	
	private ExecutorService executorService;

	private volatile boolean stop;

	public NIOEchoClientHandler(String host, int port) {
		this.host = host == null ? "127.0.0.1" : host;
		this.port = port;
		this.executorService= Executors.newSingleThreadExecutor();

		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			socketChannel.connect(new InetSocketAddress(host, port));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (!stop) {
			try {
				selector.select(1000);
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
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		// 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(executorService != null) {
			executorService.shutdown();
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			// 判断是否连接成功
			SocketChannel sc = (SocketChannel) key.channel();
			if (key.isConnectable()) {
				if (sc.finishConnect()) {
					System.out.println("连接到服务器......");
					
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					System.out.println("请输入消息[输入\"Quit\"]退出：");

					executorService.submit(() -> {
						while(true) {
							try {
								buffer.clear();
								InputStreamReader input = new InputStreamReader(System.in);
								BufferedReader br = new BufferedReader(input);
								
								String msg = br.readLine();
								
								if (msg.equals("Quit")) {
									System.out.println("关闭客户端......");
									key.cancel();
									sc.close();
									this.stop = true;
									break;
 								}
								
								buffer.put(msg.getBytes());
								buffer.flip();
								
								sc.write(buffer);
								
								System.out.println("请输入消息[输入\"Quit\"]退出：");

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}						
					});
					sc.register(selector, SelectionKey.OP_READ);
				} else {
					System.exit(1); // 连接失败，进程退出
				}
			}
			
			if (key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println(body);
					
					if(body.equals("Quit"))
					{
						this.stop = true;
					}
				} else if (readBytes < 0) {
					// 对端链路关闭
					key.cancel();
					sc.close();
				} else
					; // 读到0字节，忽略
			}
			
			if(key.isWritable()){
				 System.out.println("The key is writable");
			}
		}
	}
 

	private void doWrite(SocketChannel sc) throws IOException {
/*		System.out.println("请输入消息[输入\"Quit\"]退出：");
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		String userInput;

		while ((userInput = stdIn.readLine()) != null) {
			out.println(userInput);
			System.out.println(in.readLine());*/
			byte[] req = "QUERY TIME ORDER".getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
			writeBuffer.put(req);
			writeBuffer.flip();
			sc.write(writeBuffer);
			if (!writeBuffer.hasRemaining())
				System.out.println("Send order 2 server succeed.");

/*			
			if (userInput.equals("Quit")) {
				System.out.println("Closing client");
				out.close();
				in.close();
				stdIn.close();
				echoSocket.close();
				System.exit(1);
			}
			System.out.println("请输入消息[输入\"Quit\"]退出：");

		}*/
		
		
	}
}