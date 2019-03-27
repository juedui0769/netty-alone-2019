package com.wxg.singlethread;

/**
 * 2019年03月27日18:11:12<p></p>
 * 《JAVA编程实战》 程序清单 16-6 延长初始化占位类模式
 */
public class Demo0UseHolder {
    private Demo0UseHolder() {}
    private int num = 0;
    public int increase() {
        return ++num;
    }
    private static class SingleFactoryHolder {
        public static Demo0UseHolder instance = new Demo0UseHolder();
    }

    public static Demo0UseHolder getInstance() {
        return SingleFactoryHolder.instance;
    }


    /**
     * output:<pre>
 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
 31, 32, 33, 34, 35, 36, 37, 38, 39, 40
 41, 42, 43, 44, 45, 46, 47, 48, 49, 50
 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
 61, 62, 63, 64, 65, 66, 67, 68, 69, 70
 71, 72, 73, 74, 75, 76, 77, 78, 79, 80
 81, 82, 83, 84, 85, 86, 87, 88, 89, 90
 91, 92, 93, 94, 95, 96, 97, 9998, 100, 101, 102, 103, 104, 105, 106, 107, 108
 , 109, 110</pre>
     * 这结果是否能说明`单例`是线程安全的？
     * @param args
     */
    public static void main(String[] args) {
        Demo0UseHolder duh = Demo0UseHolder.getInstance();
        increaseAndPrint(duh);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                Demo0UseHolder duh0 = Demo0UseHolder.getInstance();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                increaseAndPrint(duh0);
            });
            thread.setDaemon(false);
            thread.start();
        }
    }

    private static void increaseAndPrint(Demo0UseHolder duh) {
        for (int i = 0; i < 10; i++) {
            System.out.print(duh.increase());
            if (i < 9) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }
}
