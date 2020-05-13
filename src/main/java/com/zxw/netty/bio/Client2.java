package com.zxw.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zxw
 * @date 2020-05-12 12:12:00
 * @Description
 */
public class Client2 {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 1000; i++) {
                Socket socket = new Socket("localhost", 10010);
                OutputStream outputStream = socket.getOutputStream();
                String text = "hello, i 'am client-" + i;
                while (true) {

                }
//                outputStream.write(text.getBytes());
//                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
