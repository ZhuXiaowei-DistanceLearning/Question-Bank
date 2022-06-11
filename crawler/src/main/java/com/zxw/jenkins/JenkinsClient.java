package com.zxw.jenkins;

/**
 * 内部带有连接池的API：对外提供一个XXXClient类，通过这个类可以直接进行服务端请
 * 求；这个类内部维护了连接池，SDK使用者无需考虑连接的获取和归还问题。一般而言，
 * XXXClient是线程安全的。对应到连接池的结构示意图中，整个API就是蓝色框包裹的部
 * 分。
 * @author zxw
 * @date 2022/3/12 12:28
 */
public class JenkinsClient {
}
