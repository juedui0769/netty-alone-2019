package ch07.classloading;

/**
 * copy at 2019年04月02日17:47:46，
 * 被动使用类字段演示二：
 * 通过数组定义来引用类，不会触发此类的初始化
 */
public class NoInitializationV2 {
    public static void main(String[] args) {
        SuperClass[] sca = new SuperClass[10];
    }
}
