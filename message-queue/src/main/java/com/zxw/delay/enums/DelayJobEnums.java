package com.zxw.delay.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author zxw
 * @date 2021/10/26 11:52
 */
@AllArgsConstructor
public enum DelayJobEnums {
    DELAY("0", "不可执行状态，等待时钟周期。"),
    READY("1", "可执行状态，等待消费"),
    RESERVED("2", "已被消费者读取，但还未得到消费者的响应（delete、finish）。"),
    DELETED("3", "已被消费完成或者已被删除。"),
    ;

    public static String getLabelByValue(String value) {
        Optional<DelayJobEnums> optional = Arrays.stream(values()).filter(v -> v.getValue().equals(value)).findFirst();
        if (optional.isPresent()) {
            return optional.get().label;
        }
        return "";
    }

    private String value;
    private String label;

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
