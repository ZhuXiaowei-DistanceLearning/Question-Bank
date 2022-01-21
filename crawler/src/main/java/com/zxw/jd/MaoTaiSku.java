package com.zxw.jd;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/1/21 10:04
 */
@Data
@Slf4j
public class MaoTaiSku {
    private String title;
    private String skuId = "100012043978";

    public String getYuShouUrl() {
        String url = "https://yushou.jd.com/youshouinfo.action?callback=fetchJSON&sku=" + skuId + "&_" + System.currentTimeMillis();
        HttpRequest get = HttpUtil.createGet(url);
        get.header("Referer", "https://item.jd.com/100012043978.html");
        get.header("User-Agent", PropertiesUtils.getUserAgent());
        JSONObject jsonObject = JdParseUtils.parseResult(get.execute().body());
        log.info("本场预约开始时间:{}", jsonObject.getString("stime"));
        log.info("本场预约结束时间:{}", jsonObject.getString("yueEtime"));
        log.info("本场预约数量:{}", jsonObject.getInteger("num"));
        log.info("本场预约抢购开始时间:{}", jsonObject.getString("qiangStime"));
        log.info("本场预约信息:{}", jsonObject.getString("info"));
        log.info("本场预约地址:{}", jsonObject.getString("url"));
        return "https:" + jsonObject.getString("url");
    }
}
