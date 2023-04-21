package com.zxw.utils;

import java.io.File;

public class DeleteLogFile {
    public static void main(String[] args) {
//        File[] roots = File.listRoots(); // 获取所有根目录
        File[] roots = new File[]{new File("D:\\code")}; // 获取所有根目录
        for (File root : roots) { // 遍历所有根目录
            deleteLogFile(root); // 删除.log结尾的文件
        }
    }

    private static void deleteLogFile(File file) {
        if (file.isDirectory()) { // 如果是目录，递归处理子文件
            File[] subFiles = file.listFiles();
            if (subFiles != null && subFiles.length > 0) {
                for (File subFile : subFiles) {
                    deleteLogFile(subFile);
                }
            }
        } else if (file.isFile() && file.getName().endsWith(".log")) { // 如果是文件，并且以.log结尾，删除文件
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("已删除文件：" + file.getAbsolutePath());
            } else {
                System.out.println("删除文件失败：" + file.getAbsolutePath());
            }
        }
    }
}