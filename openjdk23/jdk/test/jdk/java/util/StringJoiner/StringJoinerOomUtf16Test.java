/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
/**
 * @test
 * @bug 8265237
 * @summary tests StringJoiner OOME when joining sub-max-length Strings
 * @modules java.base/jdk.internal.util
 * @requires vm.bits == "64" & os.maxMemory > 4G
 * @run testng/othervm -Xmx4g -XX:+CompactStrings StringJoinerOomUtf16Test
 */

import org.testng.annotations.Test;

import static jdk.internal.util.ArraysSupport.SOFT_MAX_ARRAY_LENGTH;
import static org.testng.Assert.fail;

import java.util.StringJoiner;


@Test(groups = {"unit","string","util","libs"})
public class StringJoinerOomUtf16Test {

    // the sum of lengths of the following two strings is way less than
    // SOFT_MAX_ARRAY_LENGTH, but the byte[] array holding the UTF16 representation
    // would need to be bigger than Integer.MAX_VALUE...
    private static final String HALF_MAX_LATIN1_STRING =
        "*".repeat(SOFT_MAX_ARRAY_LENGTH >> 1);
    private static final String OVERFLOW_UTF16_STRING =
        "\u017D".repeat(((Integer.MAX_VALUE - SOFT_MAX_ARRAY_LENGTH) >> 1) + 1);

    public void OOM1() {
        try {
            new StringJoiner("")
                .add(HALF_MAX_LATIN1_STRING)
                .add(OVERFLOW_UTF16_STRING)
                .toString();
            fail("Should have thrown OutOfMemoryError");
        } catch (OutOfMemoryError ex) {
            System.out.println("Expected: " + ex);
        }
    }

    public void OOM2() {
        try {
            new StringJoiner(HALF_MAX_LATIN1_STRING)
                .add("")
                .add(OVERFLOW_UTF16_STRING)
                .toString();
            fail("Should have thrown OutOfMemoryError");
        } catch (OutOfMemoryError ex) {
            System.out.println("Expected: " + ex);
        }
    }

    public void OOM3() {
        try {
            new StringJoiner(OVERFLOW_UTF16_STRING)
                .add("")
                .add(HALF_MAX_LATIN1_STRING)
                .toString();
            fail("Should have thrown OutOfMemoryError");
        } catch (OutOfMemoryError ex) {
            System.out.println("Expected: " + ex);
        }
    }

    public void OOM4() {
        try {
            new StringJoiner("", HALF_MAX_LATIN1_STRING, OVERFLOW_UTF16_STRING)
                .toString();
            fail("Should have thrown OutOfMemoryError");
        } catch (OutOfMemoryError ex) {
            System.out.println("Expected: " + ex);
        }
    }
}

