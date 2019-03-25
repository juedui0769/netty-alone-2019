package com.wxg.atomic;

import java.util.Random;

/**
 * 2019年03月26日00:33:40 <p></p>
 *
 */
public class AtomicUpdaterTest0 {
    public static void main(String[] args) {
        Person person = new Person();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.print(person.age++ + ", ");
            }).start();
        }
    }

    private static class Person {
        int age;
    }
}
