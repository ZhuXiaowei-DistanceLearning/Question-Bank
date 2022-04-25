package com.zxw.robot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

/**
 * @author zxw
 * @date 2021-08-22 11:41
 */
public class TestRobot {
    static Double shiyongX = null;
    static Double shiyongY = null;

    static class Shut extends Thread {
        Robot robot;

        public Shut(Robot robot) {
            this.robot = robot;
        }

        @Override
        public void run() {
            clearAll(robot);
        }
    }

    public static void main(String[] args) throws AWTException, InterruptedException {
//        start();
//        PointerInfo pinfo = MouseInfo.getPointerInfo();
//        Point p = pinfo.getLocation();
        Robot robot = new Robot();
        Random random = new Random();
        Shut shut = new Shut(robot);
        shut.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(shut);

//        Scanner sc = new Scanner(System.in);

//        System.out.println("请输入应用程序个数:");
        int i = 2;
        clearAll(robot);

        Thread.sleep(2000);
        while (true) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            buttonCTRLA(robot);
            buttonEnter(robot);

//            buttonTAB(robot);
            altTab(robot);
            buttonCTRLA(robot);
            buttonEnter(robot);
            if (i == 3) {
                robot.keyPress(KeyEvent.VK_ALT);
                buttonTAB(robot);
                buttonTAB(robot);
                robot.keyRelease(KeyEvent.VK_ALT);
                buttonCTRLA(robot);
            }


            System.out.println(point.getX() + "-" + point.getY());
        }
    }

    public static void start() {

        try {
//            Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /k start C:\\Users\\zxw\\Desktop\\p78t5.exe");
            if (shiyongX == null) {
                setClickMouse(shiyongX, shiyongY);
            }
            onclick(shiyongX, shiyongY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onclick(Double x, Double y) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(x.intValue(), y.intValue());
            buttonClick(robot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setClickMouse(Double x, Double y) {
        System.out.println("获取点击坐标:");
        Scanner sc = new Scanner(System.in);
        while (true) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            System.out.println(point.getX() + "-" + point.getY());
            System.out.println("点击坐标是否正确?");
            String s = sc.nextLine();
            if ("y".equals(s.toLowerCase())) {
                x = new Double(point.getX());
                y = new Double(point.getY());
                break;
            }
        }
    }

    public static void buttonTAB(Robot robot) {
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(1000);
    }

    public static void buttonClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(1000);
    }

    public static void buttonDoubleClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(1000);
    }

    public static void buttonEnter(Robot robot) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(1000);
    }

    public static void buttonALT(Robot robot) {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.delay(1000);
    }

    public static void buttonCTRLA(Robot robot) {

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.delay(1000);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
        robot.delay(1000);
    }

    public static void altTab(Robot robot) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(2000);
    }

    public static void clearAll(Robot robot) {
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(2000);
        System.out.println("释放所有按键");
    }
}
