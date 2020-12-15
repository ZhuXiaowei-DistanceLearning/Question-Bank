package com.zxw.jvm;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2020-12-12 22:07
 */
public class ExcelImport {
    static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Throwable {
        executorService.submit(()->{
            try {
                extracted();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        extracted();
    }

    private static void extracted() throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        int size = 1000000;
        for (int rownum = 0; rownum < size; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
                if (rownum >= SpreadsheetVersion.EXCEL2007.getLastRowIndex()) {
                    sh = wb.createSheet();
                    size -= rownum;
                    rownum = 0;
                    cellnum = 0;
                }
            }
        }
        FileOutputStream out = new FileOutputStream("C:\\Users\\zxw\\Desktop/sxssf.xlsx");
        wb.write(out);
        out.close();
        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}
