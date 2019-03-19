package com.wxg.demo01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.TimeUnit;

/**
 * 2019年3月19日11:50:41<p></p>
 * 以下-好实践-坏实践-均来自 {@link io.netty.channel.ChannelFuture} javadocs 中的注释
 * <p></p>
 * {@link io.netty.channel.DefaultChannelPromise}
 * {@link DefaultPromise#awaitUninterruptibly()}
 */
public class ChannelFutureStudy {

    // BAD - NEVER DO THIS
    public void channelRead1(ChannelHandlerContext ctx, Object msg) {
        ChannelFuture future = ctx.channel().close();
        future.awaitUninterruptibly();
        // Perform post-closure operation
        // ...
    }

    // GOOD
    public void channelRead2(ChannelHandlerContext ctx, Object msg) {
        ChannelFuture future = ctx.channel().close();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // Perform post-closure operation
                // ...
            }
        });
    }



    // BAD - NEVER DO THIS
    {
        Bootstrap b = null;
        ChannelFuture f = b.connect();
        f.awaitUninterruptibly(10, TimeUnit.SECONDS);
        if (f.isCancelled()) {
            // Connection attempt cancelled by user
        } else if (!f.isSuccess()) {
            // You might get a NullPointerException here because the future
            // might not be completed yet.
            f.cause().printStackTrace();
        } else {
            // Connection established successfully
        }
    }

    // GOOD
    {
        Bootstrap b = null;
        // Configure the connect timeout option.
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        ChannelFuture f = b.connect();
        f.awaitUninterruptibly();

        // Now we are sure the future is completed.
        assert f.isDone();

        if (f.isCancelled()) {
            // Connection attempt cancelled by user
        } else if (!f.isSuccess()) {
            f.cause().printStackTrace();
        } else {
            // Connection established successfully
        }
    }

}
