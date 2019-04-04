package ch07;

import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * copy at 2019年04月04日10:44:29，
 */
public class CodeList7_4 {
    public static void main(String[] args) {
        Channel ch = new EmbeddedChannel();

        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run every 10 seconds");
            }
        }, 10, 10, TimeUnit.SECONDS);
    }
}
