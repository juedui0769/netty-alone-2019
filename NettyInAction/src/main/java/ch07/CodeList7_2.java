package ch07;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * copy at 2019年04月04日01:22:23，
 */
public class CodeList7_2 {
    public static void main(String[] args) {
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("30 seconds later");
            }
        }, 30, TimeUnit.SECONDS);

        // ...

        executor.shutdown();
    }
}
