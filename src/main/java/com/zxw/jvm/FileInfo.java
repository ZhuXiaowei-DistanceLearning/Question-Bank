package com.zxw.jvm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2020/6/29 16:12
 */
public class FileInfo {
    public static void main(String[] args) {
        System.out.println(read("mylogger"));
    }

    public static String read(String fileName){
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            return in.lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
