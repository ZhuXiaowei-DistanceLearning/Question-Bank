package com.zxw.mock;

import com.github.javafaker.Faker;
import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.MockConfig;
import com.zxw.web.po.DeviceBasicInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/5/5 9:13
 */
@Slf4j
public class MockTest {
    public static void main(String[] args) {
        Faker faker = new Faker();
        MockConfig mockConfig =
                new MockConfig()
                        .subConfig("age")
                        // 设置 int 的范围
                        .intRange(1, 100)
                        .subConfig("email")
                        // 设置生成邮箱正则
                        .stringRegex("[a-z0-9]{5,15}\\@\\w{3,5}\\.[a-z]{2,3}")
                        .globalConfig();
        DeviceBasicInfo mock = JMockData.mock(DeviceBasicInfo.class, mockConfig);
        System.out.println(mock);
    }
}
