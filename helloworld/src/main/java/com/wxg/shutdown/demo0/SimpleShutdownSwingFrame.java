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
 */
public class SimpleShutdownSwingFrame extends JFrame {

    private Set<EventLoopGroup> eventlgs = new HashSet<>();

    public SimpleShutdownSwingFrame() {}

    public void init() {
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
            setVisible(true);
        });
    }

    public void addGroup(EventLoopGroup group) {
        eventlgs.add(group);
    }

    public static void main(String[] args) {
        SimpleShutdownSwingFrame frame = new SimpleShutdownSwingFrame();
        frame.init();
    }

    private void destroy() {
        if (eventlgs.size() > 0) {
            for (EventLoopGroup elg : eventlgs) {
                elg.shutdownGracefully();
            }
        }
    }
}
