package com.zxw.netty.demo.bytebuf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Author：baiwenhuang
 * @Date: 2021/9/30 10:25 上午
 */
public class ByteBufferDemo2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向缓冲区中写入1个字节的数据
        buffer.put((byte) 97);
        // 通过工具类查看当前 buffer 的状态
        ByteBufferUtil.debugAll(buffer);

        // 向 buffer 中写入含有4个字节数据的字节数组的数据
        buffer.put(new byte[]{98, 99, 100, 101});
        ByteBufferUtil.debugAll(buffer);

        // 写模式 -> 读模式
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);

        // 读取两个数据
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);

        // 使用 compact 切换模式
        buffer.compact();
        ByteBufferUtil.debugAll(buffer);

        // 再次写入两个数据
        buffer.put((byte) 102);
        buffer.put((byte) 103);
        ByteBufferUtil.debugAll(buffer);
    }

    public void test1() {
        try (RandomAccessFile file = new RandomAccessFile("/Users/ideal/develop/study/netty/test.txt", "rw")) {
            FileChannel channel = file.getChannel();
            // 创建两个缓冲区
            ByteBuffer a = ByteBuffer.allocate(5);
            ByteBuffer b = ByteBuffer.allocate(1);
            ByteBuffer c = ByteBuffer.allocate(2);
            channel.position(11);
            a.put(new byte[]{'i', 'd', 'e', 'a', 'l'});
            b.put(new byte[]{'-'});
            c.put(new byte[]{'2', '0'});
            // 切回读模式
            a.flip();
            b.flip();
            c.flip();
            // 利用工具类看看效果
            ByteBufferUtil.debugAll(a);
            ByteBufferUtil.debugAll(b);
            ByteBufferUtil.debugAll(c);
            channel.write(new ByteBuffer[]{a, b, c});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        try (RandomAccessFile file = new RandomAccessFile("/Users/ideal/develop/study/netty/test.txt", "rw")) {
            FileChannel channel = file.getChannel();
            // 创建三个字节缓冲区
            ByteBuffer a = ByteBuffer.allocate(5);
            ByteBuffer b = ByteBuffer.allocate(1);
            ByteBuffer c = ByteBuffer.allocate(2);
            // 分别读取
            channel.read(new ByteBuffer[]{a, b, c});
            // 后面如果想进行读操作，分别 flip 即可
            a.flip();
            b.flip();
            c.flip();
            // 利用工具类看看效果
            ByteBufferUtil.debugAll(a);
            ByteBufferUtil.debugAll(b);
            ByteBufferUtil.debugAll(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串和Buffer互转
     * 编码：字符串 getBytes 转为字节数组，存入 ByteBuffer 缓冲区
     * 解码：flip -> StandardCharsets 的 decoder 方法解码
     */
    public void test3() {
        // 定义两个字符串
        String s1 = "ideal-20";
        String s2 = "";

        // 创建字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 将字符串转为字节数组存入缓冲区
        buffer.put(s1.getBytes());
        ByteBufferUtil.debugAll(buffer);

        // 写模式 -> 读模式
        buffer.flip();

        // 通过 StandardCharsets解码，获得 CharBuffer，再通过 toString 获得字符串
        s2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(s2);
        ByteBufferUtil.debugAll(buffer);
    }

    /**
     * StandardCharsets加解码
     */
    public void test4() {
        // 准备两个字符串
        String s1 = "ideal-20";
        String s2 = "";

        // 通过 StandardCharsets 的 encode 方法获得 ByteBuffer
        // 此时获得的 ByteBuffer 为读模式，无需通过 flip 切换模式
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(s1);
        ByteBufferUtil.debugAll(buffer);

        // 将缓冲区中的数据转化为字符串
        // 通过 StandardCharsets 解码，获得 CharBuffer，再通过 toString 获得字符串
        s2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(s2);
        ByteBufferUtil.debugAll(buffer);
    }

    /**
     * 4.4.3 方式三：getByte 和 warp 编码 +  StandardCharsets 解码
     * 编码：字符串调用 getByte() 方法获得字节数组，将字节数组传给 ByteBuffer的wrap() 方法，通过该方法获得 ByteBuffer。同样无需调用 flip 方法切换为读模式。
     * 解码：通过StandardCharsets的decoder方法解码
     */
    public void test5() {
        // 准备两个字符串
        String str1 = "ideal-20";
        String str2 = "";

        // 通过 StandardCharsets 的 encode 方法获得 ByteBuffer
        // 此时获得的 ByteBuffer 为读模式，无需通过 flip 切换模式
        ByteBuffer buffer1 = ByteBuffer.wrap(str1.getBytes());
        ByteBufferUtil.debugAll(buffer1);

        // 将缓冲区中的数据转化为字符串
        // 通过 StandardCharsets 解码，获得 CharBuffer，再通过 toString 获得字符串
        str2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str2);
        ByteBufferUtil.debugAll(buffer1);
    }
}