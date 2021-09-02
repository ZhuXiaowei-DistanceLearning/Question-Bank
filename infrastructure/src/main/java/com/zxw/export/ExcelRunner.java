package com.zxw.export;

import com.zxw.utils.ExcelUtils;

/**
 * @author zxw
 * @date 2021/9/2 10:54
 */
public class ExcelRunner {
    static String filePath = "D:\\360极速浏览器下载\\属性包含榨季的SKU-截至20210831.xlsx";

    public static void main(String[] args) {
        ExcelUtils.getOneColumnList(filePath, "skuID");
    }
}
