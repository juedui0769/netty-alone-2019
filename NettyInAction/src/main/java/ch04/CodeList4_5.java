package ch04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;

/**
 * copy at 2019年04月05日21:13:26，
 * 代码清单 4-5  写出到 Channel
 */
public class CodeList4_5 {
    public static void main(String[] args) {
        Channel channel = new EmbeddedChannel();

        // 创建持有要写数据的 ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        // 写数据并冲刷它
        ChannelFuture cf = channel.writeAndFlush(buf);

        // 添加 ChannelFutureListener 以便在写操作完成后接收通知
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    // 写操作完成，并且没有错误发生
                    System.out.println("Write successful");
                } else {
                    System.err.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
