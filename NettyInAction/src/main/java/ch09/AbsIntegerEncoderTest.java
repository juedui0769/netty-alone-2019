package ch09;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.EventExecutorGroup;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * copy at 2019年03月31日16:37:35 ,
 * <p>{@link EmbeddedChannel#EmbeddedChannel(ChannelHandler...)} 构造方法值得研究，</p>
 * <p>其中会调用到 {@link EmbeddedChannel#setup(boolean, ChannelHandler...)} 方法，</p>
 * <p>这个方法的内部细节很能说明问题，在这里 pipeline 被构建，handlers 被添加进 pipeline 中，</p>
 * <p>ctx就是在handler添加进 pipeline 时被构建的 -> {@link io.netty.channel.DefaultChannelPipeline#addLast(EventExecutorGroup, String, ChannelHandler)}</p>
 *
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
