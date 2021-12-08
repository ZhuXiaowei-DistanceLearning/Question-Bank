package com.zxw.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zxw.exception.BusinessException;
import com.zxw.exception.ExpMsgConsts;

import java.io.*;

/**
 * @author zxw
 * @date 2021/12/8 16:30
 */
public class MongoJsonUtils {
    static File file = new File("C:\\Users\\zxw\\Desktop/CONTRACT_SIGN_MASTER_JOB.json");

    public static void main(String[] args) throws Exception {
        InputStream in;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
            JSONArray jsonObject = JSON.parseArray(sb.toString());
            System.out.println(jsonObject);
        }
    }



    public JSONArray getDataArray() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
            JSONArray res = JSON.parseArray(sb.toString());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExpMsgConsts.COMMON_ERROR, "解析文件失败");
        }
    }
}
