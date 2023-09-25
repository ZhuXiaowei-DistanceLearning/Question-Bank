package com.zxw.netty.doc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zxw
 * @date 2023-09-25 15:54
 */
public class DocInboundHandler extends ChannelInboundHandlerAdapter {
    public DocInboundHandler() {
        super();
    }

    /**
     * 客户端向服务端发起连接请求：当客户端向服务端发起连接请求时，服务端会收到一个TCP SYN包，表示客户端想要建立连接。这时，Netty会在服务端的ChannelPipeline中触发channelRegistered事件，表示通道被注册到EventLoop上。服务端的ChannelHandler可以在这个事件中执行一些初始化的操作，例如分配缓冲区、设置超时时间等。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * 当通道从EventLoop取消注册时触发，表示通道已经不再接收任何事件或数据12。这个事件通常是在channelInactive事件之后触发，表示通道已经彻底关闭12。这个事件与网络的实际状况没有直接关系，而是由Netty内部的逻辑决定的。当Netty检测到通道已经不活跃了，就会从EventLoop上移除这个通道，并释放相关的资源 。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * 服务端接受连接请求：当服务端接受连接请求时，会向客户端发送一个TCP SYN+ACK包，表示同意建立连接，并告知客户端自己的序列号。这时，Netty会在服务端的ChannelPipeline中触发channelActive事件，表示通道处于活动状态。服务端的ChannelHandler可以在这个事件中执行一些准备工作，例如发送欢迎信息、验证身份等。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 客户端或服务端主动关闭通道：当客户端或服务端想要断开连接时，会向对方发送一个TCP FIN包，表示结束数据传输。对方收到TCP FIN包后，会回复一个TCP ACK包，并进入CLOSE_WAIT状态，等待自己发送完所有数据后再断开连接。当双方都发送完所有数据后，就会互相发送TCP FIN+ACK包，并进入TIME_WAIT状态，等待一段时间后再彻底断开连接。在这个过程中，Netty会在服务端的ChannelPipeline中触发channelInactive事件3 。
     * 网络中断：当客户端或服务端的网络出现故障，导致连接中断时，也会触发channelInactive事件。这种情况下，由于没有收到对方的TCP FIN包，所以无法正常关闭连接，而是会等待一定的超时时间后才断开连接。这个超时时间由TCP协议的参数决定，例如TCP Keepalive和TCP Retransmission Timeout 。
     * 心跳超时：当客户端或服务端在一定时间内没有收到对方的任何数据或心跳包时，也会触发channelInactive事件。这种情况下，可以认为对方已经失去响应或掉线了。Netty提供了IdleStateHandler类来实现心跳超时的检测和处理 。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 客户端确认连接：当客户端收到TCP SYN+ACK包时，会向服务端发送一个TCP ACK包，表示确认建立连接，并告知服务端自己的序列号。这时，Netty会在服务端的ChannelPipeline中触发channelRead事件，表示通道有数据可读。服务端的ChannelHandler可以在这个事件中执行一些读取操作，例如解析数据、处理逻辑等。
     * 客户端和服务端开始传输数据：当客户端和服务端建立连接后，就可以开始传输数据了。每次传输数据时，都会使用TCP协议来保证数据的可靠性和有序性。每次传输数据后，Netty都会在服务端的ChannelPipeline中触发channelRead事件和channelReadComplete事件，分别表示通道有数据可读和通道读取数据完成。服务端的ChannelHandler可以在这两个事件中执行一些读取和处理操作，例如解码、校验、回复等。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    /**
     * 客户端和服务端开始传输数据：当客户端和服务端建立连接后，就可以开始传输数据了。每次传输数据时，都会使用TCP协议来保证数据的可靠性和有序性。每次传输数据后，Netty都会在服务端的ChannelPipeline中触发channelRead事件和channelReadComplete事件，分别表示通道有数据可读和通道读取数据完成。服务端的ChannelHandler可以在这两个事件中执行一些读取和处理操作，例如解码、校验、回复等。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 当通道有用户自定义事件被触发时执行的操作，例如心跳超时、空闲检测等12。这个方法的参数是一个Object对象，表示触发的事件类型12。在这个方法中，可以根据不同的事件类型执行不同的逻辑，例如发送心跳包、关闭通道等。
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    /**
     * 当通道处理入站事件或数据时发生异常时执行的操作，例如连接断开、数据解析错误等这个方法的参数是一个Throwable对象，表示捕获到的异常。在这个方法中，可以打印或记录异常信息，并关闭或释放资源
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void ensureNotSharable() {
        super.ensureNotSharable();
    }

    @Override
    public boolean isSharable() {
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
