/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @summary Unit test for encode/decode convenience methods
 * @run junit EncDec
 */

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncDec {

    /**
     * Test that the input String is the same after round tripping
     * the Charset.encode() and Charset.decode() methods.
     */
    @ParameterizedTest
    @MethodSource("stringProvider")
    public void roundTripTest(String pre) {
        ByteBuffer bb = ByteBuffer.allocate(100);
        Charset preCs = Charset.forName("ISO-8859-15");
        if (!preCs.canEncode()) {
            throw new RuntimeException("Error: Trying to test encode and " +
                    "decode methods on a charset that does not support encoding");
        }
        bb.put(preCs.encode(pre)).flip();
        String post = Charset.forName("UTF-8").decode(bb).toString();
        assertEquals(pre, post, "Mismatch after encoding + decoding, :");
    }

    static Stream<String> stringProvider() {
        return Stream.of(
                "Hello, world!",
                "apple, banana, orange",
                "car, truck, horse");
    }
}
