package com.zxw.export;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FromHowTo2 {
    public static void main(String[] args) throws Throwable {
//        fileToZip();
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put(i,i);
        }
        System.out.println(map);
    }

    public static void fileToZip() {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        // 临时目录 C:\Users\ADMINI~1\AppData\Local\Temp\
//        String path = System.getProperty("java.io.tmpdir") + fileName;
        try {
            File zipFile = new File("C:\\Users\\ym-02\\Desktop\\a.zip");
            // 如果存在该zip文件，则删除
            zipFile.deleteOnExit();

            // 创建一个新文件
            zipFile.createNewFile();

            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];
            File subFile = new File("C:\\Users\\ym-02\\Desktop/aa.csv");
            File subFile2 = new File("C:\\Users\\ym-02\\Desktop/personlog文档.docx");
            // 文件名增加时间戳避免重复
//            String subFileName = attachment.getName() + "-" + new Date().getTime() +
//                    attachment.getSuffix();
            //创建ZIP实体，并添加进压缩包
            ZipEntry zipEntry = new ZipEntry(subFile.getName());
//            ZipEntry zipEntry2 = new ZipEntry(subFile2.getName());
            zos.putNextEntry(zipEntry);
//            zos.putNextEntry(zipEntry2);
            //读取待压缩的文件并写进压缩包里
            fis = new FileInputStream(subFile);
            bis = new BufferedInputStream(fis, 1024 * 10);
            int read = 0;
            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                zos.write(bufs, 0, read);
            }
//            fis = new FileInputStream(subFile2);
//            bis = new BufferedInputStream(fis, 1024 * 10);
//            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
//                zos.write(bufs, 0, read);
//            }
            System.out.println("压缩成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭流
            try {
                if (null != bis) bis.close();
                if (null != zos) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}