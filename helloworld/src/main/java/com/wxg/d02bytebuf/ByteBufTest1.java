package com.wxg.d02bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * 2019年3月25日15:13:40<p></p>
 */
public class ByteBufTest1 {

    private static final Charset UTF8 = Charset.forName("utf-8");

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("王hello world", UTF8);

        // UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 11, cap: 33)
        System.out.println(byteBuf);

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, UTF8));
        }

        System.out.println(byteBuf.arrayOffset());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.capacity());

        System.out.println(byteBuf.readableBytes());

        System.out.println("---");
        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            System.out.print((char)byteBuf.getByte(i));
            if (i < byteBuf.readableBytes() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("---");
        System.out.println(byteBuf.getCharSequence(0, 3, UTF8));
        System.out.println(byteBuf.getCharSequence(0, 8, UTF8));
    }
}
