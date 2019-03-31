package ch09;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * create at 2019年03月30日23:51:43，
 * 来自第09章，
 * 对照书本敲打一遍，体验还是不一样。
 *
 * update at 2019年03月31日15:53:54，
 * 因为这个项目是用来学习的，代码大多来自书本，所以不把代码放到 `src/test/java/` 目录下了，而是直接放在 `src/main/java` 目录下。
 */
public class FixedLengthFrameDecoderTest {

    /**
     * 对照书本敲打一遍后，
     * 对 {@link ByteBuf#retain()} 和 {@link ByteBuf#release()} 有了更深刻的体会！
     * {@link ByteBuf#duplicate()} 方法返回一个浅拷贝，引用计数不会增加，
     * 如果要重用ByteBuf需要调用 {@link ByteBuf#retain()} 方法，
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
        Assert.assertTrue(channel.writeInbound(input.retain()));
        Assert.assertTrue(channel.finish());

        // read messages
        ByteBuf read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
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
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

        Assert.assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }
}