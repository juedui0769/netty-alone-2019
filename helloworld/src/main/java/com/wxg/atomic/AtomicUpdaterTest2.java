package com.wxg.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 2019年03月26日01:21:28<p></p>
 * 这个输出结果，意义不大啊！！！
 */
public class AtomicUpdaterTest2 {

    public static void main(String[] args) {
        Person person = new Person();

        AtomicReferenceFieldUpdater<Person, String> updater
                = AtomicReferenceFieldUpdater.newUpdater(Person.class, String.class, "name");

        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(person.name + " -> ");
                System.out.print("[ " + updater.getAndSet(person, "name_" + num) + ", ");
                System.out.println(updater.get(person) + " ]");
            }).start();
        }
    }


    private static class Person {
        volatile String name = "wxg";
    }
}
