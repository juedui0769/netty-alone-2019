package com.wxg.codec.demo0;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.List;

public class MyCodecClientHandler0 extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output: " + msg);
//        ctx.writeAndFlush("From client : " + LocalDateTime.now());
    }

    /**
     * 这里输出 5 个 long 型的变量；可以在 {@link MyByteToLongDecoder#decode(ChannelHandlerContext, ByteBuf, List)} 的 `System.out.println(in.readableBytes());` 的输出看到变化由 40, 32, 24, 16, 8 逐次递减。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(123456L);
        ctx.writeAndFlush(1L);
        ctx.writeAndFlush(2L);
        ctx.writeAndFlush(3L);
        ctx.writeAndFlush(4L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
