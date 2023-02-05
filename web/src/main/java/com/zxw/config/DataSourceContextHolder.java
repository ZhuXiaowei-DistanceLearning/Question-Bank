package com.zxw.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ruyuan
 * 数据源上下文
 */
@Slf4j
public class DataSourceContextHolder {

    public static final String DATA_SOURCE_01 = "datasource01";
    public static final String DATA_SOURCE_02 = "datasource02";
    public static final String DATA_SOURCE_03 = "datasource03";

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceType(String dataSourceType) {
        if (dataSourceType == null) {
            log.error("dataSource为空");
            throw new NullPointerException();
        }
        log.info("设置dataSource：{}", dataSourceType);
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 根据取模结果，设置当前线程的数据源类型
     * @param userId 用户id
     */
    public static void setDataSourceType(Long userId) {
        Long result = userId % 3;

        if (result == 0) {
            DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.DATA_SOURCE_01);
        } else if (result == 1) {
            DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.DATA_SOURCE_02);
        } else {
            DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.DATA_SOURCE_03);
        }
    }

    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void removeDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
