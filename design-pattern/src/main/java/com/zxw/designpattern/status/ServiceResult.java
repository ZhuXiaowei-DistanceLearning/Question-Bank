package com.zxw.designpattern.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxw
 * @date 2021/5/20 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult<T, C> {
    private T t;
    private C c;

    public boolean isSuccess() {
        return false;
    }
}
