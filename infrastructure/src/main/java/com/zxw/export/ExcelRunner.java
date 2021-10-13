package com.zxw.export;

import com.zxw.utils.ExcelUtils;

/**
 * @author zxw
 * @date 2021/9/2 10:54
 */
public class ExcelRunner {
    static String filePath = "C:\\Users\\zxw\\Desktop/CONTRACT_SIGN_SLAVE_JOB.xlsx";

    public static void main(String[] args) {
        ExcelUtils.getOneColumnList(filePath, "userId");
    }
}
