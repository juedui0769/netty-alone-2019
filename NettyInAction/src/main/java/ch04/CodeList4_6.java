package ch04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * copy at 2019年04月05日21:19:53，
 * 代码清单 4-6 多个线程使用同一个 Channel
 * <p>
 * 将书中原有代码进行了改写，但是，没有达到我的期望，我对 {@link ByteBuf} 的理解还不够深。
 * </p>
 * {@link io.netty.handler.codec.string.StringEncoder}
 */
public class CodeList4_6 {
    public static void main(String[] args) {
        final Channel channel = new EmbeddedChannel(new InnerOutputHandler());
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8).retain();
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(buf.duplicate());
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();

        // write in one thread
        executor.execute(writer);

        // write in another thread
        executor.execute(writer);

        executor.shutdown();
    }

    private static class InnerOutputHandler extends ChannelOutboundHandlerAdapter {
        StringBuilder sbb = new StringBuilder();

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            ByteBuf buf = (ByteBuf) msg;

            while (buf.isReadable()) {
                char c = buf.readChar();
                sbb.append(c);
            }
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            super.flush(ctx);
            System.out.println(sbb.toString());
        }
    }

    private static class InnerHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            System.out.println(msg.toString());
        }
    }
}
