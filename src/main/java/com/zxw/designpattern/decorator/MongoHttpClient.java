package com.zxw.designpattern.decorator;

import com.zxw.common.datastruct.Page;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author zxw
 * @date 2020-12-16 14:29
 */
public interface MongoHttpClient {
    Page pageList();

    Page pageListBySort(int page, int limit, String sort, Class cls);

    List<T> list(Class<T> cls);

    List list(int page, int limit, Class cls);

    T findOne(Class<T> cls);

    void shutdown();
}
