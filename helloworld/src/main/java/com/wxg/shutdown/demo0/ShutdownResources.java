package com.wxg.shutdown.demo0;

import io.netty.channel.EventLoopGroup;

import java.util.HashSet;
import java.util.Set;

/**
 * 2019年03月27日19:12:06
 */
public class ShutdownResources {
    private static Set<EventLoopGroup> eventlgs = new HashSet<>();

    private ShutdownResources() {}

    public void addGroup(EventLoopGroup group) {
        eventlgs.add(group);
    }

    /**
     * 调用了 {@link EventLoopGroup#shutdownGracefully()} 之后，还需要将 {@link #eventlgs} 清空。
     */
    public void destroy() {
        System.out.println("eventlgs size : " + eventlgs.size());
        for (EventLoopGroup group : eventlgs) {
            group.shutdownGracefully();
        }
        eventlgs.clear();
    }

    public int size() {
        return eventlgs.size();
    }

    public static ShutdownResources get() {
        return ResourceHolder.instance;
    }

    @Deprecated
    private Set<EventLoopGroup> allGroups() {
        return eventlgs;
    }

    private static class ResourceHolder {
        public static ShutdownResources instance = new ShutdownResources();
    }
}
