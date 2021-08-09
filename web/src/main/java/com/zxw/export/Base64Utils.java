package com.zxw.export;

import java.io.*;
import java.util.Base64;

/**
 * @author zxw
 * @date 2021/5/12 10:46
 */
public class Base64Utils {

        static Base64.Encoder encoder = Base64.getMimeEncoder();
        static Base64.Decoder decoder = Base64.getMimeDecoder();

        /**
         * 得到Base64 字符串
         */
        public static String getBase64String(File file) {
            FileInputStream fin = null;
            BufferedInputStream bin = null;
            ByteArrayOutputStream baos = null;
            BufferedOutputStream bout = null;
            try {
                // 建立读取文件的文件输出流
                fin = new FileInputStream(file);
                // 在文件输出流上安装节点流（更大效率读取）
                bin = new BufferedInputStream(fin);
                // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
                baos = new ByteArrayOutputStream();
                // 创建一个新的缓冲输出流，以将数据写入指定的底层输出流
                bout = new BufferedOutputStream(baos);
                byte[] buffer = new byte[1024];
                int len = bin.read(buffer);
                while (len != -1) {
                    bout.write(buffer, 0, len);
                    len = bin.read(buffer);
                }
                // 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
                bout.flush();
                byte[] bytes = baos.toByteArray();
                return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes).trim();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fin.close();
                    bin.close();
                    // 关闭 ByteArrayOutputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何 IOException
                    // IOException
                    // baos.close();
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        /**
         * 将base64编码转换成文件
         */
        public static void base64StringToFile(String base64sString, String fileFullName) {
            BufferedInputStream bin = null;
            FileOutputStream fout = null;
            BufferedOutputStream bout = null;
            try {
                // 将base64编码的字符串解码成字节数组
                byte[] bytes = decoder.decode(base64sString);
                // 创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                // 创建从底层输入流中读取数据的缓冲输入流对象
                bin = new BufferedInputStream(bais);
                // 指定输出的文件
                File file = new File(fileFullName);
                // 创建到指定文件的输出流
                fout = new FileOutputStream(file);
                // 为文件输出流对接缓冲输出流对象
                bout = new BufferedOutputStream(fout);
                byte[] buffers = new byte[1024];
                int len = bin.read(buffers);
                while (len != -1) {
                    bout.write(buffers, 0, len);
                    len = bin.read(buffers);
                }
                // 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
                bout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bin.close();
                    fout.close();
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static InputStream base64StrToInputStream(String base64string) {
            ByteArrayInputStream stream = null;
            try {
                byte[] bytes1 = decoder.decode(base64string);
                stream = new ByteArrayInputStream(bytes1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stream;
        }

    public static void main(String[] args) {
        String base64String = Base64Utils.getBase64String(new File("C:\\Users\\zxw\\Desktop\\企业三方数据查询授权书（金服准入资料）.pdf"));
        System.out.println(base64String);
    }
}
