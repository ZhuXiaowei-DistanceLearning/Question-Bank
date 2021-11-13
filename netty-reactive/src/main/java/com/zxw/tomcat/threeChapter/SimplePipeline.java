package com.zxw.tomcat.threeChapter;

import org.apache.catalina.Container;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;

import java.util.Set;

/**
 * @author zxw
 * @date 2021-11-13 21:50
 */
public class SimplePipeline implements Pipeline {
    @Override
    public Valve getBasic() {
        return null;
    }

    @Override
    public void setBasic(Valve valve) {

    }

    @Override
    public void addValve(Valve valve) {

    }

    @Override
    public Valve[] getValves() {
        return new Valve[0];
    }

    @Override
    public void removeValve(Valve valve) {

    }

    @Override
    public Valve getFirst() {
        return null;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public void findNonAsyncValves(Set<String> result) {

    }

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }
}
