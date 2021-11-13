package com.zxw.tomcat.threeChapter;


import org.apache.catalina.*;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author zxw
 * @date 2021-11-13 21:44
 */
public class ClientIPLogger implements Valve, Contained {

    @Override
    public Valve getNext() {
        return null;
    }

    @Override
    public void setNext(Valve valve) {

    }

    @Override
    public void backgroundProcess() {

    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }
}
