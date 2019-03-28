package com.wxg.codec.demo0;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 2019年03月27日14:54:54
 * <h3>笔记 2019年03月28日16:13:49</h3>
 * <ul>
 * <li>{@link #decode(ChannelHandlerContext, ByteBuf, List)} 方法会被调用多次，参考父类 {@link ByteToMessageDecoder#callDecode(ChannelHandlerContext, ByteBuf, List)} 方法， 此方法是在 {@link ByteToMessageDecoder#channelRead(ChannelHandlerContext, Object)} 方法中被调用的。</li>
 * </ul>
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked!");

        System.out.println(in.readableBytes());

        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
