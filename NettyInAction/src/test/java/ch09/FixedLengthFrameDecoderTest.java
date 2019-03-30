package ch09;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * create at 2019年03月30日23:51:43，
 * 来自第09章，
 * 对照书本敲打一遍，体验还是不一样。
 */
public class FixedLengthFrameDecoderTest {

    /**
     * 对照书本敲打一遍后，
     * 对 {@link ByteBuf#retain()} 和 {@link ByteBuf#release()} 有了更深刻的体会！
     */
    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        // write bytes
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());

        // read messages
        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }

    /**
     * create at 2019年03月31日00:21:40，
     * 只有 {@link EmbeddedChannel} 可以这样吧？
     * 先调用 {@link EmbeddedChannel#writeInbound(Object...)}，
     * 再调用 {@link EmbeddedChannel#readInbound()} 来测试。
     * {@link ByteBuf#readSlice(int)} 这个方法也没怎么使用，现在是学习到了一招。
     */
    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));

        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }
}