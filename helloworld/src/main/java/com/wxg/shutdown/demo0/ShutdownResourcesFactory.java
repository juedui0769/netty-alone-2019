package com.wxg.shutdown.demo0;

/**
 * 2019年03月27日19:14:58，
 * 这里写错了，应该把 factory 放到目标类内部
 */
@Deprecated
public class ShutdownResourcesFactory {
    private static class ResourceHolder {
        // public static ShutdownResources instance = new ShutdownResources();
        public static ShutdownResources instance = null;
    }

    public static ShutdownResources get() {
        return ResourceHolder.instance;
    }
}
