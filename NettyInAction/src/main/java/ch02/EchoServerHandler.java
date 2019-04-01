package ch02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * copy at 2019年04月01日14:39:11,
 * <p>{@link EchoClientHandler} 是继承自 {@link io.netty.channel.SimpleChannelInboundHandler} 可以自动释放消息。</p>
 * <p>此类因为需要向客户端回写消息，而 {@link ChannelHandlerContext#write(Object)} 方法是异步的，</p>
 * <p>在 {@link #channelRead(ChannelHandlerContext, Object)} 完成时，write()可能还未执行，所以不能在 channelRead 方法中释放消息，</p>
 * <p>而是在 {@link #channelReadComplete(ChannelHandlerContext)} 中释放。</p>
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    /**
     * TODO: 疑问, channelReadComplete 是在什么时候被调用, 是由那个组件(类)调用的呢？
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
