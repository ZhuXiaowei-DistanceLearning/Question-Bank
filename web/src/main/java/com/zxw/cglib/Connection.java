package com.zxw.cglib;

/**
 * @author zxw
 * @date 2021-02-04 10:22
 */
public class Connection {
    private int a = 0;

    public Cursor scan() {
        return new TestCursor();
    }

    public static void main(String[] args) {
        Connection connection = new Connection();
        ConvertingCursor convertingCursor = new ConvertingCursor(connection.scan());
        System.out.println(convertingCursor);
    }
}
