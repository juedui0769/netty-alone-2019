package ch07.classloading;

public class ConstClass {
    static {
        System.out.println("ConstClass init!");
    }

    public static final String HELLO_WORLD = "hello world";
}
