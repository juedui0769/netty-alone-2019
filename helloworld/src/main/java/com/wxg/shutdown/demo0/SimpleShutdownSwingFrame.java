package com.wxg.shutdown.demo0;

import io.netty.channel.EventLoopGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * 窗体关闭事件，参考： <a href="https://blog.csdn.net/zhenshiyiqie/article/details/8440806">addWindowListener, windowClosing</a> {@link WindowAdapter}
 * <ul>
 * <li>2019年03月27日19:02:24，</li>
 * <li>经过测试，在外部调用 {@link EventLoopGroup#shutdownGracefully()} 是可以关闭Server和Client端的</li>
 * <li>单例模式应用到 Swing 窗口程序的创建上是失败了，也许是因为 {@link EventQueue#invokeLater(Runnable)} 的缘故，也许是因为其他原因；总之，创建Swing窗口程序时要格外小心；一般情况是，先创建swing窗口程序，再创建其他程序比如netty server；或者将 {@link #eventlgs} 放到另外的类中去维护，只要保证窗口关闭时能够调用到就可以了。</li>
 *
 * </ul>
 */
public class SimpleShutdownSwingFrame extends JFrame {

    private static Set<EventLoopGroup> eventlgs = new HashSet<>();

    private static SimpleShutdownSwingFrame instance;

    private SimpleShutdownSwingFrame() {
        init();
    }

    private void init() {
        EventQueue.invokeLater(() -> {
            setTitle("Welcome");
            setSize(300, 200);
            setResizable(false);
            // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Do something
                    destroy();
                    System.out.println("Exit!");
                    System.exit(0);
                }
            });
        });
    }

    public void showNow() {
        EventQueue.invokeLater(() -> {
            setVisible(true);
        });
    }

    public void addGroup(EventLoopGroup group) {
        eventlgs.add(group);
    }

    public static synchronized SimpleShutdownSwingFrame getInstance() {
        if (instance == null) {
            instance = new SimpleShutdownSwingFrame();
        }
        // return LazyInitHolder.frame;
        return instance;
    }

    public static void main(String[] args) {
        SimpleShutdownSwingFrame frame = SimpleShutdownSwingFrame.getInstance();
        frame.showNow();
        frame.showNow();
    }

    private void destroy() {
        System.out.println("eventlgs size : " + eventlgs.size());
        if (eventlgs.size() > 0) {
            for (EventLoopGroup elg : eventlgs) {
                elg.shutdownGracefully();
            }
        }
    }

    private static class LazyInitHolder {
        public static SimpleShutdownSwingFrame frame = new SimpleShutdownSwingFrame();
    }
}
