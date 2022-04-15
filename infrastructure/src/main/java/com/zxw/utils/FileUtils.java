package com.zxw.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author zxw
 * @date 2022/4/14 17:27
 */
public class FileUtils {
    public static void main(String[] args) {
        Pattern compile = Pattern.compile(".*((http.*)])");
        Stream<String> stream = FileUtils.readLine("D:\\code\\shareservice\\APEC_PJ004_DynamicData\\logs\\2022-03-30.0.dynamicdata-server-info.log");
        try (stream) {
            stream.forEach(e -> {
                if (e.contains("请求路径")) {
                    Matcher matcher = compile.matcher(e);
                    if (matcher.matches()) {
                        String group = matcher.group(1);
                    }
                    System.out.println(e);
                }
            });
        } catch (Exception e) {

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
