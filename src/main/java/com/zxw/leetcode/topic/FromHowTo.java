package com.zxw.leetcode.topic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

@Slf4j
public class FromHowTo {
    FileInputStream fis = null;
    XSSFWorkbook workbook = null;
    BufferedWriter writer = null;

    public static void main(String[] args) throws Throwable {
//        extracted();
//        writeCharset();
//        extracted();
        String s = "16C";
        System.out.println(s.substring(0, s.length() - 1));
        FromHowTo fht = new FromHowTo();
        fht.csvFileConversionCharset("C:\\Users\\ym-02\\Desktop/2020 (2)", "");
    }

    private static void extracted2() {
        File file = new File("C:\\Users\\ym-02\\Desktop\\2020\\1/2020-1-311.csv");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
            Sheet sh = wb.createSheet();
            int size = 10;
            StringBuffer sb = new StringBuffer();
            for (int rownum = 0; rownum < size; rownum++) {
                Row row = sh.createRow(rownum);
                for (int cellnum = 0; cellnum < 10; cellnum++) {
                    Cell cell = row.createCell(cellnum);
                    String address = new CellReference(cell).formatAsString();
                    sb.append(address);
                    if (cellnum == 9) {
                        sb.append("\n");
                    } else {
                        sb.append(",");
                    }
                    cell.setCellValue(address);
                    if (rownum >= SpreadsheetVersion.EXCEL2007.getLastRowIndex()) {
                        sh = wb.createSheet();
                        size -= rownum;
                        rownum = 0;
                        cellnum = 0;
                    }
                }
                System.out.print(sb.toString());
                out.write(sb.toString());
                sb = new StringBuffer();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void csvFileConversionCharset(String filePath, String charset) throws Exception {
        try {
            this.handlerFilePath(filePath, charset);
            if (null != writer) writer.close();
            if (null != workbook) workbook.close();
            if (null != fis) fis.close();
        } catch (IOException e) {
            log.error("文件转换错误，原因：{}", e.toString());
        }
    }

    public void handlerFilePath(String filePath, String charset) {
        File root = new File(filePath);
        if (!root.exists()) {
            return;
        }
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                this.handlerFilePath(files[i].getPath(), charset);
            } else {
                this.fileConversion(files[i]);
            }
        }
    }

    public void fileConversion(File file) {
        try {
            workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file)));
            StringBuilder sb = new StringBuilder();
            for (Row row : sheet) {
                for (int k = 0; k < row.getLastCellNum(); k++) {
                    String s = row.getCell(k).toString();
                    sb.append(s);
                    if (k == row.getLastCellNum() - 1) {
                        sb.append("\n");
                    } else {
                        sb.append(",");
                    }
                }
                out.write(sb.toString());
                sb = new StringBuilder();
            }
//            writer = new BufferedWriter(new FileWriter(file));
//            writer.write(sb.toString());
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void writeCharset() {
        try {
            for (int i = 1; i < 2; i++) {
                int month = i + 1;
                File file = new File("C:\\Users\\ym-02\\Desktop/2020" + "/" + month);
                if (!file.exists()) {
                    break;
                }
                File[] files = file.listFiles();
                for (int j = 0; j < files.length; j++) {
                    try {
                        fis = new FileInputStream(files[i]);
                        workbook = new XSSFWorkbook(fis);
                        XSSFSheet sheet = workbook.getSheetAt(0);
                        StringBuilder sb = new StringBuilder();
                        for (Row row : sheet) {
                            StringBuffer sbc = new StringBuffer();
                            for (int k = 0; k < row.getLastCellNum(); k++) {
                                String s = row.getCell(k).toString();
                                sbc.append(s);
                                if (k == row.getLastCellNum() - 1) {
                                    sbc.append("\n");
                                    sb.append(sbc);
                                } else {
                                    sbc.append(",");

                                }
                            }
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(files[i]);
                        fileOutputStream.write(sb.toString().getBytes());
//                        writer = new BufferedWriter(new FileWriter(files[i]));
//                        writer.write(sb.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (null != writer) writer.close();
            if (null != workbook) workbook.close();
            if (null != fis) fis.close();
        } catch (IOException e) {

        }
    }

    private static void extracted() throws IOException {
        for (int i = 0; i < 5; i++) {
            File file = new File("C:\\Users\\ym-02\\Desktop/2020" + "/" + i);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (int j = 0; j < 10; j++) {
                SXSSFWorkbook wb = new SXSSFWorkbook(-1); // keep 100 rows in memory, exceeding rows will be flushed to disk
                wb.setCompressTempFiles(true);
                Sheet sh = wb.createSheet();
                AtomicInteger ac = new AtomicInteger(0);
                int size = 1000;
                for (int rownum = 0; rownum < size; rownum++) {
                    Row row = sh.createRow(rownum);
                    if (ac.get() >= SpreadsheetVersion.EXCEL2007.getLastRowIndex()) {
                        sh = wb.createSheet();
                        size -= ac.get();
                        ac.set(0);
                        rownum = 0;
                    }
                    for (int cellnum = 0; cellnum < 10; cellnum++) {
                        Cell cell = row.createCell(cellnum);
                        String address = new CellReference(cell).formatAsString();
                        cell.setCellValue(new String(address.getBytes(), "GB2312"));
                    }
                    ac.addAndGet(1);
                }
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file.getPath() + "/" + i + "_" + j + ".csv");
                    wb.write(out);
                    out.close();
                    wb.dispose();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // dispose of temporary files backing this workbook on disk
            }
        }
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