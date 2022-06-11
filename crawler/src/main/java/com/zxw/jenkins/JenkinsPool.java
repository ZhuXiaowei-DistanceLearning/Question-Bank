package com.zxw.jenkins;

/**
 * 连接池和连接分离的API：有一个XXXPool类负责连接池实现，先从其获得连接
 * XXXConnection，然后用获得的连接进行服务端请求，完成后使用者需要归还连接。通
 * 常，XXXPool是线程安全的，可以并发获取和归还连接，而XXXConnection是非线程安全
 * 的。对应到连接池的结构示意图中，XXXPool就是右边连接池那个框，左边的客户端是我
 * 们自己的代码。
 * @author zxw
 * @date 2022/3/12 12:28
 */
public class JenkinsPool {
}
