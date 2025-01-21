/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/* @test
 * @bug 8253832
 * @run testng CoderMalfunctionErrorTest
 * @summary Check CoderMalfunctionError is thrown for any RuntimeException
 *      on CharsetDecoder.decodeLoop() invocation.
 */

import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

@Test
public class CoderMalfunctionErrorTest {
    @Test (expectedExceptions = CoderMalfunctionError.class)
    public void testDecodeLoop() {
        new CharsetDecoder(StandardCharsets.US_ASCII, 1, 1) {
            @Override
            protected CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
                throw new RuntimeException("This exception should be wrapped in CoderMalfunctionError");
            }
        }.decode(null, null, true);
    }
}
