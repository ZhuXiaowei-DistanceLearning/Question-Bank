//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zxw.netty.demo.handler.ws;

import com.zxw.utils.StringRandom;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.http.websocketx.client.WebSocketClientHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public final class WebSocketClient {
    static final String URL = System.getProperty("url", "ws://127.0.0.1:8080/websocket");

    public WebSocketClient() {
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 2)
                .forEach(e -> {
                    executorService.execute(() -> {
                        try {
                            start();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                });
    }

    private static void start() throws URISyntaxException, InterruptedException, IOException {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
        final String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            System.err.println("Only WS(S) is supported.");
        } else {
            boolean ssl = "wss".equalsIgnoreCase(scheme);
            final SslContext sslCtx;
            if (ssl) {
                sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } else {
                sslCtx = null;
            }

            NioEventLoopGroup group = new NioEventLoopGroup();

            try {
                final WebSocketClientHandler handler = new WebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String) null, true, new DefaultHttpHeaders()));
                Bootstrap b = new Bootstrap();
                ((Bootstrap) ((Bootstrap) b.group(group)).channel(NioSocketChannel.class)).handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(new ChannelHandler[]{sslCtx.newHandler(ch.alloc(), host, port)});
                        }

                        p.addLast(new ChannelHandler[]{new HttpClientCodec(), new HttpObjectAggregator(8192), WebSocketClientCompressionHandler.INSTANCE, handler});
                    }
                });
                Channel ch = b.connect(uri.getHost(), port).sync().channel();
                handler.handshakeFuture().sync();
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

                while (true) {
                    String msg = "来自用户[" + Thread.currentThread().getName() + "]发言：" + StringRandom.getStringRandom(10);
//                    String msg = console.readLine();
                    if (msg == null) {
                        break;
                    }
                    if ("bye".equals(msg.toLowerCase())) {
                        ch.writeAndFlush(new CloseWebSocketFrame());
                        ch.closeFuture().sync();
                        break;
                    }

                    if ("ping".equals(msg.toLowerCase())) {
                        WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[]{8, 1, 8, 1}));
                        ch.writeAndFlush(frame);
                    } else {
                        WebSocketFrame frame = new TextWebSocketFrame(msg);
                        ch.writeAndFlush(frame);
                    }
                }
            } finally {
                group.shutdownGracefully();
            }

        }
    }
}
