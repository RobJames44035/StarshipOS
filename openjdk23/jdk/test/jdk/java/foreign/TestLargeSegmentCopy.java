/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @requires sun.arch.data.model == "64"
 * @bug 8292851
 * @run testng/othervm -Xmx4G TestLargeSegmentCopy
 */

import org.testng.annotations.Test;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.ValueLayout.JAVA_LONG;

public class TestLargeSegmentCopy {

    @Test
    public void testLargeSegmentCopy() {
        // Make sure the byte size is bigger than Integer.MAX_VALUE
        final int longArrayLength = Integer.MAX_VALUE / Long.BYTES + 100;
        final long[] array = new long[longArrayLength];

        try (var arena = Arena.ofConfined()) {
            var segment = arena.allocate((long) longArrayLength * Long.BYTES, Long.SIZE);
            // Should not throw an exception or error
            MemorySegment.copy(segment, JAVA_LONG, 0, array, 0, longArrayLength);
            // Should not throw an exception or error
            MemorySegment.copy(array,0, segment, JAVA_LONG, 0, longArrayLength);
        }

    }

}
