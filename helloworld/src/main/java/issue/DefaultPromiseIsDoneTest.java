package issue;

import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;

/**
 * copy from <a href="https://github.com/netty/netty/issues/7712">7712</a> at 2019年03月31日12:07:11，<a href="https://zhuanlan.zhihu.com/p/34609401">知乎</a>，
 * <a href="https://stackoverflow.com/questions/48792248/does-netty-violate-the-contract-of-future-cancel-method">stack overflow</a>，
 * <a href="https://stackoverflow.com/questions/28691081/whether-method-cancel-in-java-util-concurrent-future-should-be-blocking">stack overflow : </a>
 */
public class DefaultPromiseIsDoneTest {

    private final Promise<?> defaultPromise = GlobalEventExecutor.INSTANCE.newPromise();

    public static void main(String[] args) {
        DefaultPromiseIsDoneTest main = new DefaultPromiseIsDoneTest();
        main.isDoneTest();
    }

    private void isDoneTest() {
        defaultPromise.setUncancellable();
        defaultPromise.cancel(false);
        boolean isDone = defaultPromise.isDone();
        System.out.println(isDone);
    }
}
