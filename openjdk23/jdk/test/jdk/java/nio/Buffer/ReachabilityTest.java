/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/* @test
 * @bug 8208362
 * @summary Tests reachability from source to dependent direct byte buffers
 * @run testng ReachabilityTest
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.UnaryOperator;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ReachabilityTest {

    @Test
    public void testDuplicate() {
        testReachability(ByteBuffer.allocateDirect(16384),
                         ByteBuffer::duplicate);
    }

    @Test
    public void testSlice() {
        testReachability(ByteBuffer.allocateDirect(16384),
                         ByteBuffer::slice);
    }

    @Test
    public void testViewDuplicate() {
        testReachability(ByteBuffer.allocateDirect(16384),
                         (Buffer b) -> b instanceof ByteBuffer
                                ? ((ByteBuffer) b).asIntBuffer()
                                : b.duplicate()
        );
    }

    @Test
    public void testViewSlice() {
        testReachability(ByteBuffer.allocateDirect(16384),
                         (Buffer b) -> b instanceof ByteBuffer
                                       ? ((ByteBuffer) b).asIntBuffer()
                                       : b.slice()
        );
    }

    <T> void testReachability(T t, UnaryOperator<T> b) {
        WeakReference<T> root = new WeakReference<>(t);

        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        List<WeakReference<T>> refs = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            t = b.apply(t);
            refs.add(new WeakReference<>(t, queue));
        }
        t = b.apply(t);

        boolean collected = false;
        long timeoutMillis = 100L;
        try {
            for (int tries = 0; tries < 3 && !collected; tries++) {
                System.gc();
                collected = refs.stream().map(Reference::get).anyMatch(Objects::isNull);
                if (!collected) {
                    collected = queue.remove(timeoutMillis) != null;
                    timeoutMillis *= 4;
                }
            }
        } catch (InterruptedException unexpected) {
            throw new AssertionError("unexpected InterruptedException");
        }

        // Some or all of the intermediate values must be GC'ed
        Assert.assertTrue(collected);
        // The root should never be GC'ed
        Assert.assertNotNull(root.get());

        Reference.reachabilityFence(t);
    }
}
