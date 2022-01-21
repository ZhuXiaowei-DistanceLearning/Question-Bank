package com.zxw.jd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zxw
 * @date 2022/1/21 10:38
 */
public class JdParseUtils {
    public static JSONObject parseResult(String result) {
        int first = result.indexOf("{");
        int last = result.indexOf("}") + 1;
        JSONObject jsonObject = JSON.parseObject(result.substring(first, last));
        return jsonObject;
    }
}
