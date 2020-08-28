package com.hankutech.ax.centralserver.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Socket 通用服务端
 *
 * @author ZhangXi
 */
@Slf4j
public class SocketServer {

    /**
     * socket server字典
     * key: 服务端口
     * value: {@link SocketServer}
     */
    private static ConcurrentHashMap<Integer, SocketServer> SOCKET_SERVER_MAP = new ConcurrentHashMap<>();

    private final int port;
    private int maxClientSize = 1024;
    private int timeoutMillis = 10000;

    private ChannelFuture channelFuture;
    private final ChannelInitializer initializer;
    NioEventLoopGroup bossGroup;
    NioEventLoopGroup workerGroup;

    public SocketServer(int port, ChannelInitializer initializer) {
        this.port = port;
        this.initializer = initializer;
    }

    /**
     * 启动socket服务器
     *
     * @throws NettyServerException Netty服务端异常
     */
    public void start() throws NettyServerException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 重用端口
                    .option(ChannelOption.SO_REUSEADDR, true)
                    // 服务端接收连接的队列长度
                    .option(ChannelOption.SO_BACKLOG, this.maxClientSize)
                    // 连接超时时间10秒
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.timeoutMillis)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(this.initializer);

            // 绑定端口并且开始接收连接请求

            this.channelFuture = bootstrap.bind(port).sync();
            if (this.channelFuture.isSuccess()) {
                log.info("socket服务端：{} 启动成功！", this.port);
                // 保存到Map
                SOCKET_SERVER_MAP.put(this.port, this);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 关闭socket服务器
     */
    public void shutdown() {
        if (null != channelFuture) {
            channelFuture.channel().close();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        SOCKET_SERVER_MAP.remove(port);
        log.info("socket服务端：{} 已关闭！", port);

    }

    public void sendData(Object msg, ChannelId... channelIds) {
        ChannelGroups.broadcast(msg, channelIds);
    }

    /**
     * 获取{@link SocketServer}指定实例
     *
     * @param port 服务端口
     * @return {@link SocketServer}
     */
    public static SocketServer getServer(int port) {

        return SOCKET_SERVER_MAP.get(port);
    }


    public SocketServer maxClientSize(int size) {
        this.maxClientSize = size;
        return this;
    }

    public SocketServer timeoutMillis(int millis) {
        this.timeoutMillis = millis;
        return this;
    }

}
