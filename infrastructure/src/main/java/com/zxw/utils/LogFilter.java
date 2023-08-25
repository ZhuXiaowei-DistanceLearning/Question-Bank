package com.zxw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

public class LogFilter {

    public static void main(String[] args) {
        // 指定日志文件所在的目录
        File dir = new File("D:\\data\\logs\\mkttsc-us");
        // 创建一个FileFilter实例，根据文件名是否以.log结尾来筛选文件
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".log");
            }
        };
        // 调用listFiles方法，传入FileFilter实例，返回符合条件的文件数组
        File[] files = dir.listFiles(filter);
        // 遍历文件数组
        for (File file : files) {
            try {
                // 创建BufferedReader对象，用于逐行读取文件内容
                BufferedReader br = new BufferedReader(new FileReader(file));
                // 定义一个字符串变量，用于存储每一行的内容
                String line = null;
                // 循环读取每一行，直到读到null为止
                while ((line = br.readLine()) != null) {
                    // 判断该行是否包含"ThusMessageFusion"和"数据时间"这两个关键词
                    if (line.contains("ThusMessageFusion") && line.contains("MSG_ORDER2")) {
                        // 如果是，就打印该行到控制台
                        System.out.println(line);
                    }
                }
                // 关闭BufferedReader对象
                br.close();
            } catch (IOException e) {
                // 如果发生异常，打印异常信息
                e.printStackTrace();
            }
        }
    }
}
