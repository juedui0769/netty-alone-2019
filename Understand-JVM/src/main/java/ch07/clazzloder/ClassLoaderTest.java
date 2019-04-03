package ch07.clazzloder;

import java.io.IOException;
import java.io.InputStream;

/**
 * copy at 2019年04月03日13:23:51，
 * copy from 7.4.1 类与类加载器，
 * 代码清单7-8 不同的类加载器对 instanceof 关键字运算的结果的影响
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[is.available()];
                    is.read(b);

                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("ch07.clazzloder.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());

        System.out.println(obj instanceof ch07.clazzloder.ClassLoaderTest);
    }
}
