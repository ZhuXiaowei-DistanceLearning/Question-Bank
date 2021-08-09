package com.zxw.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * @author zxw
 * @date 2021-02-06 14:16
 */
@Slf4j
public class FormHowTo3 {
    private FileInputStream fis = null;
    private XSSFWorkbook workbook = null;
    private BufferedWriter writer = null;
    private StringBuffer sb = new StringBuffer();
    private BufferedWriter out = null;
    private int day = 0;
    private int num = 0;
    private String monthString = "";
    private File file1 = null;
    // 文件名序列号
    private int number = 1;
    private int MAX_ROW = 100;
    //  生成的月份
    private int month = 1;
    // 文件生成地址
    private String targetPath;
    // 奇数天目录
    private String oddPath;
    // 偶数天目录
    private String evenPath;
    //  2月份目录
    private String FebPath;
    boolean skipHeader = false;
    private DateCsvCallBack callBack;
    private String fileExtension;

    public static void main(String[] args) {
        // 使用示例
        FormHowTo3.builder()
                .setCallBack(new DateCsvCallBack() {
                    @Override
                    public void doInStart(StringBuffer row, int rowNum) {

                    }

                    @Override
                    public void doInEnd(StringBuffer row, int rowNum) {

                    }

                    @Override
                    public String doInCell(String cellReference, String formattedValue, XSSFComment comment, int month, int day) {
                        if (!cellReference.equals("A1")) {
                            if (cellReference.toString().substring(0, 1).equals("A")) {
                                if (day < 10) {
                                    formattedValue = formattedValue.substring(0, 10) + month + "0" + day + formattedValue.substring(14);
                                } else {
                                    formattedValue = formattedValue.substring(0, 10) + month + day + formattedValue.substring(14);
                                }
                            }
                        }
                        return formattedValue;
//                        if (!cellReference.equals("K1")) {
//                            if (cellReference.toString().substring(0, 1).equals("K")) {
//                                if (day < 10) {
//                                    formattedValue = formattedValue.substring(0, 4) + month + "0" + day + formattedValue.substring(8);
//                                } else {
//                                    formattedValue = formattedValue.substring(0, 4) + month + day + formattedValue.substring(9);
//                                }
//
//                            }
//                        }
                    }
                })
                .setEvenPath("C:\\Users\\ym-02\\Desktop\\2020 (2)\\9")
                .setFebPath("C:\\Users\\ym-02\\Desktop\\2020 (2)\\2")
                .setOddPath("C:\\Users\\ym-02\\Desktop\\2020 (2)\\10")
                .setMonth(6)
                .setSkipHeader(true)
                .setTargetPath("C:\\Users\\ym-02\\Desktop\\2020 (2)\\new")
                .start();
    }

    /**
     * 将一个目录中所有的csv文件数据合并到一个csv文件中
     */
    public void merge() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        Date currentDate = calendar.getTime();
        this.file1 = new File(targetPath + "/cp_" + (currentDate.getYear() + 1900) + "-" + this.month + "_" + this.number + "." + fileExtension);
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(this.file1, false)));
            int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if ((actualMaximum & 1) == 1 && actualMaximum >= 30) {
                this.csvFileConversionCharset(oddPath, "");
            } else if ((actualMaximum & 1) == 0 && actualMaximum >= 30) {
                this.csvFileConversionCharset(evenPath, "");
            } else {
                this.csvFileConversionCharset(FebPath, "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * 定义回调函数
     *
     * @param dateCsvCallBack
     */
    private void setCallBack(DateCsvCallBack dateCsvCallBack) {
        this.callBack = dateCsvCallBack;
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
            if (((num != 0 && !skipHeader) || rowNum != 0) || num == 0) {
                if (callBack != null) {
                    callBack.doInStart(sb, rowNum);
                }
                // If there were gaps, output the missing rows
//            if (rowNum == 0) {
//                sb.append("xxzj");
//            } else {
//                sb.append(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//            }
//            sb.append(",");
                outputMissingRows(rowNum - currentRow - 1);
                // Prepare for this row
                firstCellOfRow = true;
                currentRow = rowNum;
                currentCol = -1;
            }
        }

        @Override
        public void endRow(int rowNum) {
            if (((num != 0 && !skipHeader) || rowNum != 0) || num == 0) {
                if (callBack != null) {
                    callBack.doInEnd(sb, rowNum);
                }
//            // Ensure the minimum number of columns
//            for (int i = currentCol; i < 18; i++) {
//                stringBuffer.append(',');
//            }
//            if (rowNum == 0) {
//                sb.append(",QHDM");
//            } else {
//                sb.append(",440113");
//            }
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
        }

        @Override
        public void cell(String cellReference, String formattedValue,
                         XSSFComment comment) {
            Integer cellNum = Integer.valueOf(cellReference.substring(1));
            if (((num != 0 && !skipHeader) || cellNum != 1) || num == 0) {
                if (firstCellOfRow) {
                    firstCellOfRow = false;
                } else {
                    sb.append(',');
                }

                // gracefully handle missing CellRef here in a similar way as XSSFCell does
                if (cellReference == null) {
                    cellReference = new CellAddress(currentRow, currentCol).formatAsString();
                }

                if (callBack != null) {
                    formattedValue = callBack.doInCell(cellReference, formattedValue, comment, month, day);
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
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
            // Skip, no headers or footers in CSV
        }
    }

    public interface DateCsvCallBack {
        void doInStart(StringBuffer row, int rowNum);

        void doInEnd(StringBuffer row, int rowNum);

        String doInCell(String cellReference, String formattedValue,
                        XSSFComment comment, int month, int day);
    }

    public FormHowTo3(int month, String targetPath, String oddPath, String evenPath, String febPath, DateCsvCallBack callBack, String fileExtension, boolean skipHeader) {
        this.month = month;
        this.targetPath = targetPath;
        this.oddPath = oddPath;
        this.evenPath = evenPath;
        this.FebPath = febPath;
        this.callBack = callBack;
        this.fileExtension = fileExtension;
        this.skipHeader = skipHeader;
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private int month = 1;
        private String targetPath;
        private String oddPath;
        private String evenPath;
        private String FebPath;
        private DateCsvCallBack callBack;
        private boolean skipHeader;
        private String fileExtension = "csv";

        public Builder setMonth(int month) {
            this.month = month;
            return this;
        }

        public Builder setTargetPath(String targetPath) {
            this.targetPath = targetPath;
            return this;
        }

        public Builder setOddPath(String oddPath) {
            this.oddPath = oddPath;
            return this;
        }

        public Builder setEvenPath(String evenPath) {
            this.evenPath = evenPath;
            return this;
        }

        public Builder setFebPath(String febPath) {
            this.FebPath = febPath;
            return this;
        }

        public Builder setCallBack(DateCsvCallBack callBack) {
            this.callBack = callBack;
            return this;
        }

        public Builder setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public Builder setSkipHeader(boolean b) {
            this.skipHeader = b;
            return this;
        }

        public void start() {
            FormHowTo3 dateCsvDataMergeUtils = new FormHowTo3(month, targetPath, oddPath, evenPath, FebPath, callBack, fileExtension, skipHeader);
            dateCsvDataMergeUtils.merge();
        }
    }
}
