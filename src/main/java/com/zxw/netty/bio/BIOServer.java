package com.zxw.netty.bio;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2020-05-12 11:11:45
 * @Description
 */
@Slf4j
public class BIOServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(10010)) {
            ExecutorService executorService = Executors.newCachedThreadPool();

            log.info("服务端已启动，监听端口:{}", serverSocket.getLocalPort());

            while (true) {
                Socket accept = serverSocket.accept();
                executorService.execute(() -> {
                    log.info("接收到连接:{}", accept.getPort());
                    BufferedReader in = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(accept.getInputStream()));
//                        byte[] b = new byte[1024];
                        String request;
//                        String response;
                        while ((request = in.readLine()) != null) {
                            log.info(new String(request.getBytes()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
