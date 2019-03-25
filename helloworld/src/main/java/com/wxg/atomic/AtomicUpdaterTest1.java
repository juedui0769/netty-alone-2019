package com.wxg.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 2019年03月26日01:08:13<p></p>
 * {@link io.netty.buffer.AbstractReferenceCountedByteBuf} 使用了 {@link AtomicIntegerFieldUpdater} ,
 * 按照张龙老师的解释，ByteBuf的子类很多，使用很频繁，使用 AtomicIntegerFieldUpdater
 * 可以只在 AbstractReferenceCountedByteBuf 中维持一个 static 变量
 * 就可以作用到所有的子类上了。
 * 相对于在每个类中使用 {@link java.util.concurrent.atomic.AtomicInteger} 这样做能够节省内存提高性能。
 */
public class AtomicUpdaterTest1 {
    public static void main(String[] args) {
        Person person = new Person();

        AtomicIntegerFieldUpdater<Person> updater
                = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.print(updater.getAndIncrement(person) + ", ");
            }).start();
        }
    }

    private static class Person {
        volatile int age;
    }
}
