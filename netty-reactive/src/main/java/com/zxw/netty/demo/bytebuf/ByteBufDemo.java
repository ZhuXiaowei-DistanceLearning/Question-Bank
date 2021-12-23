package com.zxw.netty.demo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author zxw
 * @date 2021/12/23 13:37
 */
public class ByteBufDemo {
    public static void main(String[] args) {
//        test();
        test2();
    }

    /**
     * 堆缓冲区是ByteBuf最常见的实现模式
     * 其实将数据存储在JVM的堆内存中，内部实现是array数据，所以这种模式也成为数组支撑
     * 可以通过hasArray() 方法判断其内部实现是否是数组支撑，如果是的话，我们可以安全的使用arrayOffset()方法来获取偏移量结合readableBytes()来获取其底层实现，即获取byte[]
     */
    public static void test() {
        Charset charset = Charset.forName("utf-8");
        ByteBuf stringByteBuf = Unpooled.copiedBuffer("hello world", charset);
        if (stringByteBuf.hasArray()) {
            byte[] array = stringByteBuf.array();
            // as this, arrayOffset === 0
            int startIndex = stringByteBuf.arrayOffset() + stringByteBuf.readerIndex();
            // return value = writeIndex - readIndex
            int length = stringByteBuf.readableBytes();
            byte[] newBytes = Arrays.copyOfRange(array, startIndex, length);
            System.out.println("数组支撑转换:" + new String(newBytes, charset));
        }
    }

    /**
     * 直接缓冲区是ByteBuf的另外一种实现的模式，但是其内存分配是操作系统实现的，且内存并非在堆内存上。JavaDoc的
     * 文档指出
     * <p>
     * 直接缓冲区的内容将会驻留在常规的会被垃圾回收的堆内存之外
     * <p>
     * 这也就说明了为什么直接缓冲区是对于网络数据传输的最理想的方式,但是直接缓冲区相对于堆缓冲区来说，其分配和释放都比较昂贵，如果需要进行直接缓冲区的数据字节操作，你首先需要的是进入数据的复制操作，下面的代码是基于直接缓冲区的读操作：
     */
    public static void test2() {
        Charset charset = Charset.forName("utf-8");
        ByteBuf stringByteBuf = Unpooled.copiedBuffer("hello world", charset);

        if (stringByteBuf.hasArray()) {
            int lenght = stringByteBuf.readableBytes();
            byte[] bytes = new byte[lenght];
            stringByteBuf.getBytes(stringByteBuf.readerIndex(), bytes);
            System.out.println(new String(bytes, charset));
        }
    }

    public static void test3() {
        ByteBuf buffer = Unpooled.buffer(10);
        System.out.println("写数据之前ByteBuf的writeIndex=" + buffer.writerIndex());
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.setByte(i, i);
        }
        System.out.println("写数据之后ByteBuf的writeIndex=" + buffer.writerIndex());
        // 写数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.setByte(i, i);
        }
        // 循环遍历，有数据继续读取
        while (buffer.isReadable()) {
            System.out.print(buffer.readByte());
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.setByte(i, i);
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.getByte(i));
        }
        // 写入7个数据
        for (int i = 0; i < 7; i++) {
            buffer.writeByte(i);
        }

        // 输出数据
        while (buffer.isReadable()) {
            System.out.print(buffer.readByte());
            System.out.print(",");
        }
        System.out.println();
        // 打印当前索引位置
        int writeIndex = buffer.writerIndex();
        // 重置索引
        buffer.clear();

        // 写入两个数据
        buffer.writeByte(10).writeByte(10);
        // 将writeIndex索引设置到{writeIndex}
        buffer.writerIndex(writeIndex);
        // 输出数据,可见0和1被覆盖了，其他的还是原来的数据
        while (buffer.isReadable()) {
            System.out.print(buffer.readByte());
            System.out.print(",");
        }
    }

    public void test5() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(6, 10);
        printByteBufInfo("ByteBufAllocator.buffer(5, 10)", buffer);
        buffer.writeBytes(new byte[]{1, 2});
        printByteBufInfo("write 2 Bytes", buffer);
        buffer.writeInt(100);
        printByteBufInfo("write Int 100", buffer);
        buffer.writeBytes(new byte[]{3, 4, 5});
        printByteBufInfo("write 3 Bytes", buffer);
        byte[] read = new byte[buffer.readableBytes()];
        buffer.readBytes(read);
        printByteBufInfo("readBytes(" + buffer.readableBytes() + ")", buffer);
        printByteBufInfo("BeforeGetAndSet", buffer);
        System.out.println("getInt(2): " + buffer.getInt(2));
        buffer.setByte(1, 0);
        System.out.println("getByte(1): " + buffer.getByte(1));
        printByteBufInfo("AfterGetAndSet", buffer);

    }

    private static void printByteBufInfo(String step, ByteBuf buffer) {
        System.out.println("------" + step + "-----");
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
    }
}
