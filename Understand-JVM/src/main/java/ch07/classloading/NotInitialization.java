package ch07.classloading;

/**
 * copy at 2019年04月02日17:24:15，
 * 非主动使用类字段演示
 */
public class NotInitialization {
    public static void main(String[] args) {
        System.out.println(SubClass.value);
    }
}
