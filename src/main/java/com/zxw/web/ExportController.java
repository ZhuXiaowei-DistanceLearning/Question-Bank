package com.zxw.web;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zxw
 * @date 2020-12-11 16:04
 */
@RestController
@RequestMapping("export")
public class ExportController {
    @GetMapping()
    public void reportData(HttpServletResponse response) throws Exception {
        String fileName = "aaa.zip";
        response.setHeader("content-disposition", "attchment;filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(out));
            byte[] bufs = new byte[1024 * 10];
            for (int i = 0; i < 10; i++) {
                SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
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
                        cell.setCellValue(address);
                    }
                }
//            String subFileName = attachment.getName() + "-" + new Date().getTime() +
//                    attachment.getSuffix();
                //创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(i + "a.csv");
                zos.putNextEntry(zipEntry);
                //读取待压缩的文件并写进压缩包里
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                wb.write(byteArrayOutputStream);
                InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                byte[] bytes = byteArrayOutputStream.toByteArray();
                bis = new BufferedInputStream(is, 1024 * 10);
                int read = 0;
                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                    zos.write(bufs, 0, read);
                }
            }
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
