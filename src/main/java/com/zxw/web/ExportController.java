package com.zxw.web;

import com.zxw.datastruct.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zxw
 * @date 2020-12-11 16:04
 */
@RestController
@RequestMapping("export")
@Slf4j
public class ExportController {
    FileInputStream fis = null;
    XSSFWorkbook workbook = null;
    BufferedWriter writer = null;

    /**
     * 导出服务
     * @author zxw
     * @param page 分页参数
     * @param response
     * @throws Exception
     */
    @GetMapping()
    public void reportData(Pageable page, HttpServletResponse response) throws Exception {
        String fileName = "aaa.zip";
        response.setHeader("content-disposition", "attchment;filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        InputStream is = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(out));
            byte[] bufs = new byte[1024 * 10];
            for (int i = 0; i < 10; i++) {
                SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
                DataFormat dataFormat = wb.createDataFormat();
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
                        SXSSFCell cell = (SXSSFCell) row.createCell(cellnum);
                        String address = new CellReference(cell).formatAsString();
                        cell.setCellValue(new String(address.getBytes(StandardCharsets.UTF_8), "GB2312"));
                    }
                }
                //创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(i + "a.csv");
                zos.putNextEntry(zipEntry);
                //读取待压缩的文件并写进压缩包里
                byteArrayOutputStream = new ByteArrayOutputStream();
                wb.write(byteArrayOutputStream);
                is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
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

    @GetMapping("/csvFileConversionCharset")
    public void csvFileConversionCharset(String filePath, String charset, HttpServletResponse response) throws Exception {
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
            fis = new FileInputStream(file);
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
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
