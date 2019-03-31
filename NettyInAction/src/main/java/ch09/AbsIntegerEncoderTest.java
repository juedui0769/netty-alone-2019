package ch09;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * copy at 2019年03月31日16:37:35
 */
public class AbsIntegerEncoderTest {

    /**
     * 这里创建的{@link ByteBuf} buf 并没有调用 {@link ByteBuf#release()} 方法，
     * <ul>
     * <li>是因为 {@link EmbeddedChannel#writeOutbound(Object...)} -></li>
     * <li>{@link io.netty.channel.AbstractChannel#write(Object)} -></li>
     * <li>{@link io.netty.channel.DefaultChannelPipeline#write(Object)} -></li>
     * <li>{@link io.netty.channel.AbstractChannelHandlerContext#write(Object, ChannelPromise)} -></li>
     * <li>{@link io.netty.util.ReferenceCountUtil#release(Object)}</li>
     * </ul>
     * channel -> pipeline -> ctx -> util#release
     */
    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        // read bytes
        for (int i = 1; i < 10; i++) {
            assertEquals(i, (int)channel.readOutbound());
        }

        assertNull(channel.readOutbound());
    }
}
