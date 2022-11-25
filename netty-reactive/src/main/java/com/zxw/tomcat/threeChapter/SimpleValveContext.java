package com.zxw.tomcat.threeChapter;

import jakarta.servlet.ServletException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import java.io.IOException;

/**
 * @author zxw
 * @date 2021-11-13 21:51
 */
public class SimpleValveContext extends ValveBase {
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        getNext().invoke(request, response);
    }
}
