/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.internal.net.http.websocket;

import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;
import static jdk.internal.net.http.websocket.Frame.Masker.transferMasking;
import static jdk.internal.net.http.websocket.TestSupport.forEachBufferPartition;
import static jdk.internal.net.http.websocket.TestSupport.fullCopy;

public class MaskerTest {

    private static final SecureRandom random = new SecureRandom();

    @Test
    public void stateless() {
        IntStream.iterate(0, i -> i + 1).limit(125).boxed()
                .forEach(r -> {
                    int m = random.nextInt();
                    ByteBuffer src = createSourceBuffer(r);
                    ByteBuffer dst = createDestinationBuffer(r);
                    verify(src, dst, maskArray(m), 0,
                            () -> transferMasking(src, dst, m));
                });
    }

    /*
     * Stateful masker to make sure setting a mask resets the state as if a new
     * Masker instance is created each time
     */
    private final Frame.Masker masker = new Frame.Masker();

    @Test
    public void stateful0() {
        // This size (17 = 8 + 8 + 1) should test all the stages
        // (galloping/slow) of masking good enough
        int N = 17;
        ByteBuffer src = createSourceBuffer(N);
        ByteBuffer dst = createDestinationBuffer(N);
        int mask = random.nextInt();
        forEachBufferPartition(src,
                buffers -> {
                    int offset = 0;
                    masker.mask(mask);
                    int[] maskBytes = maskArray(mask);
                    for (ByteBuffer s : buffers) {
                        offset = verify(s, dst, maskBytes, offset,
                                () -> masker.transferMasking(s, dst));
                    }
                });
    }

    @Test
    public void stateful1() {
        int m = random.nextInt();
        masker.mask(m);
        ByteBuffer src = ByteBuffer.allocate(0);
        ByteBuffer dst = ByteBuffer.allocate(16);
        verify(src, dst, maskArray(m), 0,
                () -> masker.transferMasking(src, dst));
    }

    private static int verify(ByteBuffer src,
                              ByteBuffer dst,
                              int[] maskBytes,
                              int offset,
                              Runnable masking) {
        ByteBuffer srcCopy = fullCopy(src);
        ByteBuffer dstCopy = fullCopy(dst);
        masking.run();
        int srcRemaining = srcCopy.remaining();
        int dstRemaining = dstCopy.remaining();
        int masked = Math.min(srcRemaining, dstRemaining);
        // 1. position check
        assertEquals(src.position(), srcCopy.position() + masked);
        assertEquals(dst.position(), dstCopy.position() + masked);
        // 2. masking check
        src.position(srcCopy.position());
        dst.position(dstCopy.position());
        for (; src.hasRemaining() && dst.hasRemaining();
             offset = (offset + 1) & 3) {
            assertEquals(dst.get(), src.get() ^ maskBytes[offset]);
        }
        // 3. corruption check
        // 3.1 src contents haven't changed
        int srcPosition = src.position();
        int srcLimit = src.limit();
        src.clear();
        srcCopy.clear();
        assertEquals(src, srcCopy);
        src.limit(srcLimit).position(srcPosition); // restore src
        // 3.2 dst leading and trailing regions' contents haven't changed
        int dstPosition = dst.position();
        int dstInitialPosition = dstCopy.position();
        int dstLimit = dst.limit();
        // leading
        dst.position(0).limit(dstInitialPosition);
        dstCopy.position(0).limit(dstInitialPosition);
        assertEquals(dst, dstCopy);
        // trailing
        dst.limit(dst.capacity()).position(dstLimit);
        dstCopy.limit(dst.capacity()).position(dstLimit);
        assertEquals(dst, dstCopy);
        // restore dst
        dst.position(dstPosition).limit(dstLimit);
        return offset;
    }

    private static ByteBuffer createSourceBuffer(int remaining) {
        int leading = random.nextInt(4);
        int trailing = random.nextInt(4);
        byte[] bytes = new byte[leading + remaining + trailing];
        random.nextBytes(bytes);
        return ByteBuffer.wrap(bytes).position(leading).limit(leading + remaining);
    }

    private static ByteBuffer createDestinationBuffer(int remaining) {
        int leading = random.nextInt(4);
        int trailing = random.nextInt(4);
        return ByteBuffer.allocate(leading + remaining + trailing)
                .position(leading).limit(leading + remaining);
    }

    private static int[] maskArray(int mask) {
        return new int[]{
                (byte) (mask >>> 24),
                (byte) (mask >>> 16),
                (byte) (mask >>>  8),
                (byte) (mask >>>  0)
        };
    }
}
