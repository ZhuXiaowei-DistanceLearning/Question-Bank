package com.zxw.tomcat.threeChapter;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author zxw
 * @date 2021-11-13 14:46
 */
public class ResponseWriter extends PrintWriter {
    public ResponseWriter(Writer out) {
        super(out);
    }
}
