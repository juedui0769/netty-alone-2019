package ch04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * copy at 2019年04月05日18:15:40，
 * 代码清单 4-4 使用 Netty 的异步网络处理，
 * <p>和 {@link NettyOioServer} 几乎一模一样，除了
 * <ul>
 * <li>{@link OioEventLoopGroup} -> {@link NioEventLoopGroup}</li>
 * <li>{@link OioServerSocketChannel} -> {@link NioServerSocketChannel}</li>
 * </ul>
 */
public class NettyNioServer {
    public void serve(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", CharsetUtil.UTF_8));
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建 ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 使用 OioEventLoopGroup 以允许阻塞模式
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 指定 ChannelInitializer ，对于每个已接受的连接都调用它
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加一个 ChannelInboundHandlerAdapter 以拦截和处理事件
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    // 将消息写到客户端，并添加 ChannelFutureListener 以便消息一写完就关闭连接
                                    ctx.writeAndFlush(buf.duplicate())
                                            .addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            // 绑定服务器以接受连接
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            // 释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
