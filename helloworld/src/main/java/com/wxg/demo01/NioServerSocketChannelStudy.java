package com.wxg.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.channels.SelectableChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * 2019年3月19日14:35:31 <p></p>
 * `NioServerSocketChannel` 是通过反射生成的（调用无参构造方法），但是它的父类没有无参构造方法，那么代码上是怎么处理的呢？
 * <ul>
 *     <li>`NioServerSocketChannel`有三个构造方法，其他两个最终都会调用到这个构造方法 {@link NioServerSocketChannel#NioServerSocketChannel(ServerSocketChannel)} </li>
 *     <li>在这个构造方法中有对父类的调用 <code>super(null, channel, SelectionKey.OP_ACCEPT);</code></li>
 * </ul>
 *
 * <p></p>
 * See <a href="https://github.com/netty/netty/issues/2308">#2308</a> github 上的一个 issue
 * <ol>
 *     <li>{@link SelectorProvider#provider()} 这个方法内部是加锁的，这就是#2038建议缓存此方法返回结果的原因吧?</li>
 *     <li>{@link SelectorProvider#openSocketChannel()}</li>
 *     <li>{@link SocketChannel#open()}</li>
 * </ol>
 * <p></p>
 * <ol>
 *     <li>{@link NioServerSocketChannel#javaChannel()} -> {@link AbstractNioChannel#javaChannel()} 获得java.nio的channel</li>
 *     <li>{@link AbstractNioChannel#AbstractNioChannel(Channel, SelectableChannel, int)} 最后一个参数对应：
 *          <ol>
 *              <li>{@link java.nio.channels.SelectionKey#OP_ACCEPT}</li>
 *              <li>{@link java.nio.channels.SelectionKey#OP_CONNECT}</li>
 *              <li>{@link java.nio.channels.SelectionKey#OP_READ}</li>
 *              <li>{@link java.nio.channels.SelectionKey#OP_WRITE}</li>
 *          </ol>
 *     </li>
 *     <li></li>
 * </ol>
 *
 * <ol>
 *     <li>{@link io.netty.channel.socket.nio.NioServerSocketChannel}</li>
 *     <li>{@link io.netty.channel.socket.nio.NioServerSocketChannel#DEFAULT_SELECTOR_PROVIDER}</li>
 *     <li>{@link io.netty.channel.socket.nio.NioServerSocketChannel#newSocket(SelectorProvider)}</li>
 *     <li>{@link SelectorProvider#openServerSocketChannel()}</li>
 * </ol>
 */
@Deprecated
public class NioServerSocketChannelStudy {
    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(null)
                .childHandler(null);
    }
}
