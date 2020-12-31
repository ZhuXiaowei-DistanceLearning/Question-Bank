package com.zxw.leetcode.topic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFComment;
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
    StringBuffer sb = new StringBuffer();
    BufferedWriter out = null;
    static int day = 0;
    int num = 0;
    static Set<Integer> set = new HashSet<>();
    String month = "";
    File file1 = null;
    int number = 1;
    int monthIndex = 1;
    static int MAX_ROW = 100;
    Random hourRandom = new Random();
    Random minRandom = new Random();
    Random secRandom = new Random();

    public static void main(String[] args) throws Throwable {
//            workbook = new XSSFWorkbook(file);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 12; i <= 12; i++) {
            FromHowTo fht = new FromHowTo();
            fht.monthIndex = i;
            fht.month = String.valueOf(i);
            if (i < 10) {
                fht.month = "0" + i;
            }
            executorService.execute(() -> {
                fht.file1 = new File("C:\\Users\\ym-02\\Desktop\\2020 (2)\\111" + "/bak_" + "2020-" + fht.monthIndex + "_" + fht.number + ".csv");
                try {
                    fht.out = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(fht.file1, false)));
                    if (fht.monthIndex == 1 || fht.monthIndex == 3 || fht.monthIndex == 5 || fht.monthIndex == 7 || fht.monthIndex == 8 || fht.monthIndex == 10 || fht.monthIndex == 12) {
                        fht.csvFileConversionCharset("C:\\Users\\ym-02\\Desktop/2020/8", "");
                    } else if (fht.monthIndex == 2) {
                        fht.csvFileConversionCharset("C:\\Users\\ym-02\\Desktop/2020/7", "");
                    } else {
                        fht.csvFileConversionCharset("C:\\Users\\ym-02\\Desktop/2020/9", "");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            StringBuilder sb = new StringBuilder();
//            int num = 1;
//        extracted();
//        writeCharset();
//        extracted();
        String s = "16C";
//        FromHowTo fht = new FromHowTo();
//        fht.csvFileConversionCharset("C:\\Users\\ym-02\\Desktop/2020/9", "");
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
                        String lineSeparator = System.getProperty("line.separator", "/n");
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
                System.out.println("执行完毕");
                System.out.println("当前文件共" + num);
            }
        }
        System.out.println("总行数:" + num);
        System.out.println("执行完毕");
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileConversion(File file) {
        try {
//            if (num > MAX_ROW) {
//                this.out.flush();
//                this.out.close();
//                number++;
//                num = 0;
//                System.out.println("当前页:"+number);
//                this.out = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream(file1.getParentFile() + "/bak_" + "2020-" + monthIndex + "_" + number + ".csv", true)));
//            }
            System.out.println("当前执行文件:" + file.getName());
//            workbook = new XSSFWorkbook(file);
            String[] split = file.getName().split("-");
            day = Integer.valueOf(split[2].split("\\.")[0]);
            OPCPackage p = OPCPackage.open(file.getPath(), PackageAccess.READ);
            this.process(p, file, out);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void process(OPCPackage opcPackage, File file, BufferedWriter out) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String sheetName = iter.getSheetName();
            processSheet(styles, strings, new SheetToCSV(), stream, out);
            stream.close();
            ++index;
        }
    }

    /**
     * Parses and shows the content of one sheet
     * using the specified styles and shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     * @param out
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            XSSFSheetXMLHandler.SheetContentsHandler sheetHandler,
            InputStream sheetInputStream, BufferedWriter out)
            throws IOException, ParserConfigurationException, SAXException {
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(
                    styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    private class SheetToCSV implements XSSFSheetXMLHandler.SheetContentsHandler {
        private boolean firstCellOfRow = false;
        private int currentRow = -1;
        private int currentCol = -1;

        private void outputMissingRows(int number) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < number; i++) {
                for (int j = 0; j < 18; j++) {
                    stringBuffer.append(',');
                }
                stringBuffer.append('\n');
            }
        }

        @Override
        public void startRow(int rowNum) {
            // If there were gaps, output the missing rows
            if (rowNum == 0) {
                sb.append("xxzj");
            } else {
                sb.append(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            }
            sb.append(",");
            outputMissingRows(rowNum - currentRow - 1);
            // Prepare for this row
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
//            // Ensure the minimum number of columns
//            for (int i = currentCol; i < 18; i++) {
//                stringBuffer.append(',');
//            }
            if (rowNum == 0) {
                sb.append(",QHDM");
            } else {
                sb.append(",440113");
            }
            String lineSeparator = System.getProperty("line.separator", "/n");
            sb.append(lineSeparator);
            try {
                out.write(sb.toString());
                out.flush();
                sb = new StringBuffer();
                num++;
            } catch (IOException e) {
                e.printStackTrace();
                sb = new StringBuffer();
            }
        }

        @Override
        public void cell(String cellReference, String formattedValue,
                         XSSFComment comment) {
            if (firstCellOfRow) {
                firstCellOfRow = false;
            } else {
                sb.append(',');
            }

            // gracefully handle missing CellRef here in a similar way as XSSFCell does
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }
            if (!cellReference.equals("A1")) {
                if (cellReference.toString().substring(0, 1).equals("A")) {
                    if (day < 10) {
                        formattedValue = formattedValue.substring(0, 10) + month + "0" + day + formattedValue.substring(14);
                    } else {
                        formattedValue = formattedValue.substring(0, 10) + month + day + formattedValue.substring(14);
                    }
                }
            }
            if (!cellReference.equals("J1")) {
                if (cellReference.toString().substring(0, 1).equals("J")) {
                    int hour = hourRandom.nextInt(24);
                    int min = minRandom.nextInt(60);
                    int second = secRandom.nextInt(60);
                    String hourStr = String.valueOf(hour);
                    String minStr = String.valueOf(min);
                    String secondStr = String.valueOf(second);
                    if (hour < 10) {
                        hourStr = "0" + hourStr;
                    }
                    if (min < 10) {
                        minStr = "0" + minStr;
                    }
                    if (second < 10) {
                        secondStr = "0" + secondStr;
                    }
                    if (day < 10) {
                        formattedValue = formattedValue.substring(0, 4) + month + "0" + day + hourStr + minStr + secondStr;
                    } else {
                        formattedValue = formattedValue.substring(0, 4) + month + day + hourStr + minStr + secondStr;
                    }

                }
            }
            // Did we miss any cells?
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                sb.append(',');
            }
            currentCol = thisCol;

            // Number or string?
            try {
                sb.append(formattedValue);
            } catch (NumberFormatException e) {
                sb.append(formattedValue);
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
            // Skip, no headers or footers in CSV
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