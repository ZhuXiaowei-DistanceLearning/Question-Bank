package com.zxw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author zxw
 * @date 2022/4/14 17:27
 */
public class FileUtils {

    static long fileLength;

    public static void main(String[] args) {
        File file = new File("D:\\code");
        clearLog(file);
        System.out.println(fileLength / 1024 / 1024);
//        Pattern url = Pattern.compile(".*((http.*)])");
//        Pattern time = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))([ ])([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])).*");
//        Stream<String> stream = FileUtils.readLine("D:\\code\\shareservice\\APEC_PJ004_DynamicData\\logs\\2022-03-30.0.dynamicdata-server-info.log");
//        try (stream) {
//            stream.forEach(e -> {
//                if (e.contains("请求路径")) {
//                    Matcher matcher = url.matcher(e);
//                    Matcher matcher1 = time.matcher(e);
//                    if (matcher.matches()) {
//                        String group = matcher.group(1);
//                    }
//                    if (matcher1.matches()) {
//                        String group = matcher1.group(1);
//                    }
//                    System.out.println(e);
//                }
//            });
//        } catch (Exception e) {
//
//        }
    }

    public static void clearLog(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    clearLog(file1);
                }
            }
        } else {
            if (file.getAbsolutePath().endsWith(".log")) {
                System.out.println("文件名:" + file.getName());
                fileLength += file.length();
                file.deleteOnExit();
            }
        }
    }

    public static Stream<String> readLine(String file) {
        try {
            return Files.lines(Paths.get(file));
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    public static Stream<String> readFileInputStream(String file) {
        byte[] bytes = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);) {
            int i;
            while ((i = fis.read(bytes)) != -1) {

            }
            return Files.lines(Paths.get(file));
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }
}
