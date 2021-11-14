package com.zxw.tomcat.threeChapter.connector;

import lombok.Data;

/**
 * @author zxw
 * @date 2021-11-14 11:43
 */
@Data
public class HttpRequestLine {
    private String method;
    private String methodEnd;
    private String protocol;
    private String uri;
    private String uriEnd;
}
