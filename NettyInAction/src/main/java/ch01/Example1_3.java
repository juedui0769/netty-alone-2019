package ch01;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * copy at 2019年03月31日20:29:40
 *
 */
public class Example1_3 {
    public static void main(String[] args) {
        Channel channel = new NioSocketChannel();

        // Does not block
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
    }
}
