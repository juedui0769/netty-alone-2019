package com.wxg.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 2019年03月26日01:08:13<p></p>
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
