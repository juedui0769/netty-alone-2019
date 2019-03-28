package com.wxg.codec.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 2019年03月28日16:42:23
 */
public class MyByteToLongDecoderV2 extends ReplayingDecoder<Void> {

    /**
     * 对比 {@link com.wxg.codec.demo0.MyByteToLongDecoder#decode(ChannelHandlerContext, ByteBuf, List)} 方法，这里的代码简洁很多。
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoderV2 decode invoked!");

        out.add(in.readLong());
    }
}
