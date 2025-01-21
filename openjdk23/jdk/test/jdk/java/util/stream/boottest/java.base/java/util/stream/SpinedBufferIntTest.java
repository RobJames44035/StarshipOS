/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package java.util.stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.IntConsumer;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test
public class SpinedBufferIntTest extends AbstractSpinedBufferTest {
    @DataProvider(name = "IntSpinedBuffer")
    public Object[][] createIntSpinedBuffer() {
        List<Object[]> params = new ArrayList<>();

        for (int size : SIZES) {
            int[] array = IntStream.range(0, size).toArray();
            SpinedBuffer.OfInt sb = new SpinedBuffer.OfInt();
            Arrays.stream(array).forEach(sb);

            params.add(new Object[]{array, sb});
        }

        return params.toArray(new Object[0][]);
    }

    @Test(dataProvider = "IntSpinedBuffer")
    public void testIntSpliterator(int[] array, SpinedBuffer.OfInt sb) {
        assertEquals(sb.count(), array.length);
        assertEquals(sb.count(), sb.spliterator().getExactSizeIfKnown());

        SpliteratorTestHelper.testIntSpliterator(sb::spliterator);
    }

    @Test(dataProvider = "IntSpinedBuffer", groups = { "serialization-hostile" })
    public void testIntLastSplit(int[] array, SpinedBuffer.OfInt sb) {
        Spliterator.OfInt spliterator = sb.spliterator();
        Spliterator.OfInt split = spliterator.trySplit();
        long splitSizes = (split == null) ? 0 : split.getExactSizeIfKnown();
        long lastSplitSize = spliterator.getExactSizeIfKnown();
        splitSizes += lastSplitSize;

        assertEquals(splitSizes, array.length);

        List<Integer> contentOfLastSplit = new ArrayList<>();
        spliterator.forEachRemaining((IntConsumer) contentOfLastSplit::add);

        assertEquals(contentOfLastSplit.size(), lastSplitSize);

        List<Integer> end = Arrays.stream(array)
                .boxed()
                .skip(array.length - lastSplitSize)
                .collect(Collectors.toList());
        assertEquals(contentOfLastSplit, end);
    }

    @Test(groups = { "serialization-hostile" })
    public void testIntSpinedBuffer() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        SpinedBuffer.OfInt sb = new SpinedBuffer.OfInt();
        for (int i = 0; i < TEST_SIZE; i++) {
            list1.add(i);
            sb.accept(i);
        }
        PrimitiveIterator.OfInt it = sb.iterator();
        for (int i = 0; i < TEST_SIZE; i++) {
            list2.add(it.nextInt());
        }
        assertFalse(it.hasNext());
        assertEquals(list1, list2);

        for (int i = 0; i < TEST_SIZE; i++) {
            assertEquals(sb.get(i), i, Integer.toString(i));
        }

        list2.clear();
        sb.forEach((int i) -> list2.add(i));
        assertEquals(list1, list2);
        int[] array = sb.asPrimitiveArray();
        list2.clear();
        for (int i : array) {
            list2.add(i);
        }
        assertEquals(list1, list2);
    }
}
