package com.wxg.demo01;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.SocketAddress;
import java.nio.channels.spi.SelectorProvider;

/**
 * 2019-3-19 01:57:42 <p></p>
 * {@link io.netty.bootstrap.AbstractBootstrap#doBind(SocketAddress)}
 * {@link AbstractBootstrap#initAndRegister()}
 * <p></p>
 * {@link ReflectiveChannelFactory#newChannel()}
 * <p></p>
 * {@link NioServerSocketChannel#newSocket(SelectorProvider)}
 *
 */
public class MyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(null);

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
