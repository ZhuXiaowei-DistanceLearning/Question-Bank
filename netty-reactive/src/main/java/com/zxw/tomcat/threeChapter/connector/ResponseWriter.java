package com.zxw.tomcat.threeChapter.connector;

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

    @Override
    public void print(char c) {
        super.print(c);
    }

    @Override
    public void print(int i) {
        super.print(i);
    }

    @Override
    public void print(long l) {
        super.print(l);
    }

    @Override
    public void print(float f) {
        super.print(f);
    }

    @Override
    public void print(double d) {
        super.print(d);
    }

    @Override
    public void print(char[] s) {
        super.print(s);
    }

    @Override
    public void print(String s) {
        super.print(s);
    }

    @Override
    public void print(Object obj) {
        super.print(obj);
    }

    @Override
    public void println() {
        super.println();
    }

    @Override
    public void println(boolean x) {
        super.println(x);
    }

    @Override
    public void println(char x) {
        super.println(x);
    }

    @Override
    public void println(int x) {
        super.println(x);
    }

    @Override
    public void println(long x) {
        super.println(x);
    }

    @Override
    public void println(float x) {
        super.println(x);
    }

    @Override
    public void println(double x) {
        super.println(x);
    }

    @Override
    public void println(char[] x) {
        super.println(x);
    }

    @Override
    public void println(String x) {
        super.println(x);
    }

    @Override
    public void println(Object x) {
        super.println(x);
    }
}
