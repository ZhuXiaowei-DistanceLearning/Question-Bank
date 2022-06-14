package com.zxw.utils;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2021/9/2 10:46
 */
@Slf4j
public class ExcelUtils {

    public static void getOneColumnList(String filePath, String colName) {
        try {
            AtomicInteger size = new AtomicInteger();
            HashSet<String> set = new HashSet<>();
            List<Map> list = importMapExcel(filePath);
            String res = list.stream().filter(e -> set.add(String.valueOf(e.get(colName)))).map(e -> {
                size.getAndIncrement();
                return "\"" + String.valueOf(e.get(colName)).replaceAll("'", "") + "\"";
            }).collect(Collectors.joining(",", "[", "]"));
            log.info(res);
            log.info("数量:{}", size.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * excel 导入
     *
     * @param filePath  excel文件路径
     * @param pojoClass pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Class<T> pojoClass) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        try (FileInputStream in = new FileInputStream(filePath)) {
            return new ExcelImportService().importExcelByIs(in, pojoClass, params, false).getList();
        } catch (ExcelImportException e) {
            throw new ExcelImportException(e.getType(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ExcelImportException(e.getMessage(), e);
        }
    }

    /**
     * excel 导入
     *
     * @param filePath excel文件路径
     * @param <T>
     * @return
     */
    public static <T> List<T> importMapExcel(String filePath) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        try (FileInputStream in = new FileInputStream(filePath)) {
            return new ExcelImportService().importExcelByIs(in, Map.class, params, false).getList();
        } catch (ExcelImportException e) {
            throw new ExcelImportException(e.getType(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ExcelImportException(e.getMessage(), e);
        }
    }
}
