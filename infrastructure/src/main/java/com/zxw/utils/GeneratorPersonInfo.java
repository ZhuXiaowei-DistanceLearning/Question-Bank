package com.zxw.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxw
 * @date 2021/8/30 16:56
 */
public class GeneratorPersonInfo {
    static Logger log = LoggerFactory.getLogger(GeneratorPersonInfo.class);

    public static void main(String[] args) {
        String phoneNum = RandomPhoneNum.getPhoneNum();
        String idCard = CreateIDCardNo.getRandomID();
        log.info("生成虚拟用户信息,手机号:{},证件号:{}", phoneNum, idCard);
    }
}
