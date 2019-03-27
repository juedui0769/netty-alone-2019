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
     * <ul>
     * <li>2019年03月27日19:40:57</li>
     * <li>{@link io.netty.bootstrap.ServerBootstrap} 和 {@link io.netty.bootstrap.Bootstrap} 不在同一个 JVM 中，所以，想简单的维护服务端和客户端的 {@link EventLoopGroup} 是不现实的。</li>
     * <li>将它们维护在 Reids 中，或者 消息队列 中，才是正确的！</li>
     * <li>另外，只有多个 Server 是需要维护的，client 交给用户自己来维护</li>
     * </ul>
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
