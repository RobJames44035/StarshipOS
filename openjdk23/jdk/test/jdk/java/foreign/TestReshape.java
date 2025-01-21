/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @run testng TestReshape
 */

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.SequenceLayout;
import java.lang.foreign.ValueLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.LongStream;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class TestReshape {

    @Test(dataProvider = "shapes")
    public void testReshape(MemoryLayout layout, long[] expectedShape) {
        long flattenedSize = LongStream.of(expectedShape).reduce(1L, Math::multiplyExact);
        SequenceLayout seq_flattened = MemoryLayout.sequenceLayout(flattenedSize, layout);
        assertDimensions(seq_flattened, flattenedSize);
        for (long[] shape : new Shape(expectedShape)) {
            SequenceLayout seq_shaped = seq_flattened.reshape(shape);
            assertDimensions(seq_shaped, expectedShape);
            assertEquals(seq_shaped.flatten(), seq_flattened);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidReshape() {
        SequenceLayout seq = MemoryLayout.sequenceLayout(4, ValueLayout.JAVA_INT);
        seq.reshape(3, 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadReshapeInference() {
        SequenceLayout seq = MemoryLayout.sequenceLayout(4, ValueLayout.JAVA_INT);
        seq.reshape(-1, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadReshapeParameterZero() {
        SequenceLayout seq = MemoryLayout.sequenceLayout(4, ValueLayout.JAVA_INT);
        seq.reshape(0, 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadReshapeParameterNegative() {
        SequenceLayout seq = MemoryLayout.sequenceLayout(4, ValueLayout.JAVA_INT);
        seq.reshape(-2, 2);
    }

    static void assertDimensions(SequenceLayout layout, long... dims) {
        SequenceLayout prev = null;
        for (int i = 0 ; i < dims.length ; i++) {
            if (prev != null) {
                layout = (SequenceLayout)prev.elementLayout();
            }
            assertEquals(layout.elementCount(), dims[i]);
            prev = layout;
        }
    }

    static class Shape implements Iterable<long[]> {
        long[] shape;

        Shape(long... shape) {
            this.shape = shape;
        }

        public Iterator<long[]> iterator() {
            List<long[]> shapes = new ArrayList<>();
            shapes.add(shape);
            for (int i = 0 ; i < shape.length ; i++) {
                long[] inferredShape = shape.clone();
                inferredShape[i] = -1;
                shapes.add(inferredShape);
            }
            return shapes.iterator();
        }
    }

    static MemoryLayout POINT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
    );

    @DataProvider(name = "shapes")
    Object[][] shapes() {
        return new Object[][] {
                { ValueLayout.JAVA_BYTE, new long[] { 256 } },
                { ValueLayout.JAVA_BYTE, new long[] { 16, 16 } },
                { ValueLayout.JAVA_BYTE, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_BYTE, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_BYTE, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_BYTE, new long[] { 8, 16, 2 } },

                { ValueLayout.JAVA_SHORT, new long[] { 256 } },
                { ValueLayout.JAVA_SHORT, new long[] { 16, 16 } },
                { ValueLayout.JAVA_SHORT, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_SHORT, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_SHORT, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_SHORT, new long[] { 8, 16, 2 } },

                { ValueLayout.JAVA_CHAR, new long[] { 256 } },
                { ValueLayout.JAVA_CHAR, new long[] { 16, 16 } },
                { ValueLayout.JAVA_CHAR, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_CHAR, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_CHAR, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_CHAR, new long[] { 8, 16, 2 } },

                { ValueLayout.JAVA_INT, new long[] { 256 } },
                { ValueLayout.JAVA_INT, new long[] { 16, 16 } },
                { ValueLayout.JAVA_INT, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_INT, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_INT, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_INT, new long[] { 8, 16, 2 } },

                { ValueLayout.JAVA_LONG, new long[] { 256 } },
                { ValueLayout.JAVA_LONG, new long[] { 16, 16 } },
                { ValueLayout.JAVA_LONG, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_LONG, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_LONG, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_LONG, new long[] { 8, 16, 2 } },

                { ValueLayout.JAVA_FLOAT, new long[] { 256 } },
                { ValueLayout.JAVA_FLOAT, new long[] { 16, 16 } },
                { ValueLayout.JAVA_FLOAT, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_FLOAT, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_FLOAT, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_FLOAT, new long[] { 8, 16, 2 } },

                { ValueLayout.JAVA_DOUBLE, new long[] { 256 } },
                { ValueLayout.JAVA_DOUBLE, new long[] { 16, 16 } },
                { ValueLayout.JAVA_DOUBLE, new long[] { 4, 4, 4, 4 } },
                { ValueLayout.JAVA_DOUBLE, new long[] { 2, 8, 16 } },
                { ValueLayout.JAVA_DOUBLE, new long[] { 16, 8, 2 } },
                { ValueLayout.JAVA_DOUBLE, new long[] { 8, 16, 2 } },

                { POINT, new long[] { 256 } },
                { POINT, new long[] { 16, 16 } },
                { POINT, new long[] { 4, 4, 4, 4 } },
                { POINT, new long[] { 2, 8, 16 } },
                { POINT, new long[] { 16, 8, 2 } },
                { POINT, new long[] { 8, 16, 2 } },
        };
    }
}
