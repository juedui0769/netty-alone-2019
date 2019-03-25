package com.wxg.d02bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 2019年3月25日14:29:06 <p></p>
 * <a href="https://www.bilibili.com/video/av33707223?t=225&p=78">bilibili,ByteBuf,张龙</a>
 */
public class ByteBufTest0 {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.getByte(i));
            if (i != buffer.capacity() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println();

        while (buffer.isReadable()) {
            System.out.print(buffer.readByte());
            if (buffer.readerIndex() != buffer.writerIndex()) {
                System.out.print(", ");
            }
        }
    }
}
