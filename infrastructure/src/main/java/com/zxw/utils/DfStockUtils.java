package com.zxw.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2024-10-12 14:36
 */
public class DfStockUtils {

    static String cookie = "qgqp_b_id=ba3177948349d129dcefcbf6def53842; websitepoptg_api_time=1728714550270; st_si=60985281698821; EMFUND1=null; EMFUND2=null; EMFUND3=null; EMFUND4=null; EMFUND5=null; EMFUND6=null; EMFUND7=null; EMFUND8=null; EMFUND0=null; EMFUND9=10-12 14:29:20@#$%u534E%u590F%u6807%u666E500ETF%u53D1%u8D77%u5F0F%u8054%u63A5%28QDII%29C@%23%24018065; st_asi=delete; fund_registerAd_2=1; st_pvi=96750581793705; st_sp=2023-10-13%2013%3A54%3A20; st_inirUrl=https%3A%2F%2Fquote.eastmoney.com%2Fsz000858.html; st_sn=7; st_psi=20241012143646634-112200305283-2647105209";

    static String host = "api.fund.eastmoney.com";

    static String secChUa = "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"";

    static String referer = "https://fundf10.eastmoney.com/";

    static String accept = "*/*";

    public static void main(String[] args) {
        // Okhttp请求
        OkHttpClient client = new OkHttpClient();
        List<String> date = new ArrayList<>();
        List<String> value = new ArrayList();
        int page = 1;
        while (true) {
            // 设置请求头
            Request request = new Request.Builder()
                    .url("https://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183043887964289923964_1728715012516&fundCode=018065&pageIndex=" + page + "&pageSize=100&startDate=&endDate=&_=1728715012561")
                    .addHeader("cookie", cookie)
                    .addHeader("host", host)
                    .addHeader("referer", referer)
                    .addHeader("sec-ch-ua", secChUa)
                    .addHeader("accept", accept)
                    .build();
            page++;
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    // 去除前缀和后缀，提取有效 JSON 部分
                    int startIndex = res.indexOf('(') + 1;
                    int endIndex = res.lastIndexOf(')');
                    String validJson = res.substring(startIndex, endIndex);
                    // 使用 FastJSON 解析 JSON
                    JSONObject jsonObject = JSONObject.parseObject(validJson);
                    JSONObject dataObject = jsonObject.getJSONObject("Data");
                    if (dataObject != null) {
                        // 获取 LSJZList
                        JSONArray lsjzList = dataObject.getJSONArray("LSJZList");
                        if (lsjzList.isEmpty()) {
                            break;
                        }
                        for (Object item : lsjzList) {
                            if (item instanceof JSONObject) {
                                JSONObject jsonItem = (JSONObject) item;
                                date.add(jsonItem.getString("FSRQ"));
                                value.add(jsonItem.getString("DWJZ"));
                            }
                        }
                    }
                } else {
                    System.out.println("Request failed with code: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(date);
        Collections.reverse(value);
        String dates = date.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
        String values = value.stream().collect(Collectors.joining(","));
        System.out.println("日期:" + dates);
        System.out.println("日期:" + values);
    }

    public static void trim(StringBuilder sb) {
        // 去除末尾多余的逗号
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
            int lastCommaIndex = sb.lastIndexOf(",");
            if (lastCommaIndex > 0) {
                sb.delete(lastCommaIndex, lastCommaIndex + 1);
            }
        }
    }
}
