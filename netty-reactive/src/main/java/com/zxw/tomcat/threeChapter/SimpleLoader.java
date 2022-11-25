package com.zxw.tomcat.threeChapter;

import org.apache.catalina.Context;
import org.apache.catalina.Loader;

import java.beans.PropertyChangeListener;

/**
 * @author zxw
 * @date 2021-11-13 21:46
 */
public class SimpleLoader implements Loader {
    @Override
    public void backgroundProcess() {

    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public boolean getDelegate() {
        return false;
    }

    @Override
    public void setDelegate(boolean delegate) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public boolean modified() {
        return false;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
