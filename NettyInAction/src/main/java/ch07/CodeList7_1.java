package ch07;

import java.util.List;

/**
 * <p>copy at 2019年04月03日22:41:14，
 * 代码清单 7-1，在事件循环中执行任务。</p>
 * <p>{@link io.netty.channel.EventLoopGroup},</p>
 * <p>{@link io.netty.channel.EventLoop},</p>
 * <p>{@link io.netty.util.concurrent.EventExecutor},</p>
 * <p>{@link io.netty.util.concurrent.EventExecutorGroup}</p>
 */
public class CodeList7_1 {
    public static void main(String[] args) {
        boolean terminated = false;
        while (!terminated) {
            // 阻塞，直到有事件已经就绪可被运行
            List<Runnable> readyEvents = blockUntilEventReady();
            for (Runnable ev : readyEvents) {
                ev.run();
            }
        }
    }

    private static List<Runnable> blockUntilEventReady() {
        return null;
    }
}
