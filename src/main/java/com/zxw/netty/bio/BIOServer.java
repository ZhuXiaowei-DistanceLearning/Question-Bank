package com.zxw.netty.bio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zxw
 * @date 2020-05-12 11:11:45
 * @Description
 */
public class BIOServer {
    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            ServerSocket serverSocket = new ServerSocket(10010);
            System.out.println("服务端已启动，监听端口:" + serverSocket.getLocalPort());
            while (true) {
                Socket accept = serverSocket.accept();
                executorService.execute(new Thread(() -> {
                    System.out.println("接收到连接:" + accept.getPort());
                    BufferedReader in = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                        byte[] b = new byte[1024];
                        String request, response;
                        while ((request = in.readLine()) != null) {
                            System.out.println(new String(request.getBytes()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
