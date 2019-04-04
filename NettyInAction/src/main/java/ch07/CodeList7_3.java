package ch07;

import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * copy at 2019年04月04日10:26:04，
 * <p>如果使用 {@link NioSocketChannel} 或者 {@link NioServerSocketChannel} 会出现 'java.lang.IllegalStateException: channel not registered to an event loop'</p>
 * <p>使用 {@link EmbeddedChannel} 不会报错，但是调用 `future.get()` 或者 `future.sync()` 会报错 'io.netty.util.concurrent.BlockingOperationException:'</p>
 */
public class CodeList7_3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Channel ch = new EmbeddedChannel();

        ScheduledFuture<?> future = ch.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("10 seconds later");
            }
        }, 10, TimeUnit.SECONDS);
        Object o = future.get();
        System.out.println(o);
//        ch.closeFuture().sync();
//        future.sync();
    }
}
