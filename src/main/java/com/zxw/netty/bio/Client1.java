package com.zxw.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zxw
 * @date 2020-05-12 12:12:00
 * @Description
 */
public class Client1 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 10010);
            OutputStream outputStream = socket.getOutputStream();
            String text = "hello, i 'am client1";
//            outputStream.write(text.getBytes());
//            socket.close();
            while (true){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
