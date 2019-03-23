package test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/**
 * 2019年3月19日19:08:19 <p></p>
 * {@link AtomicReferenceFieldUpdater.AtomicReferenceFieldUpdaterImpl#AtomicReferenceFieldUpdaterImpl(Class, Class, String, Class)}
 * <p></p>
 * 在阅读上面代码时发现以`final`关键字声明的变量，可以稍后赋值，与我过去的认知有偏差，所以，特意编写代码证实一下。
 */
@Deprecated
public class TestFinalField {
    public static void main(String[] args) {
        final int a;
        a = 10;
    }
}
