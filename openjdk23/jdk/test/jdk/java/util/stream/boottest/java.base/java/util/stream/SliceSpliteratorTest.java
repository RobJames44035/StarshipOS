/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package java.util.stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.SpliteratorTestHelper;

import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertEquals;

/**
 * @bug 8012987
 */
@Test
public class SliceSpliteratorTest extends LoggingTestCase {

    static class UnorderedContentAsserter<T> implements SpliteratorTestHelper.ContentAsserter<T> {
        Collection<T> source;

        UnorderedContentAsserter(Collection<T> source) {
            this.source = source;
        }

        @Override
        public void assertContents(Collection<T> actual, Collection<T> expected, boolean isOrdered) {
            if (isOrdered) {
                assertEquals(actual, expected);
            }
            else {
                assertEquals(actual.size(), expected.size());
                assertTrue(source.containsAll(actual));
            }
        }
    }

    interface SliceTester {
        void test(int size, int skip, int limit);
    }

    @DataProvider(name = "sliceSpliteratorDataProvider")
    public static Object[][] sliceSpliteratorDataProvider() {
        List<Object[]> data = new ArrayList<>();

        // SIZED/SUBSIZED slice spliterator

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Integer> source =  IntStream.range(0, size).boxed().collect(toList());

                SpliteratorTestHelper.testSpliterator(() -> {
                    Spliterator<Integer> s = Arrays.spliterator(source.stream().toArray(Integer[]::new));

                    return new StreamSpliterators.SliceSpliterator.OfRef<>(s, skip, limit);
                });
            };
            data.add(new Object[]{"StreamSpliterators.SliceSpliterator.OfRef", r});
        }

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Integer> source =  IntStream.range(0, size).boxed().collect(toList());

                SpliteratorTestHelper.testIntSpliterator(() -> {
                    Spliterator.OfInt s = Arrays.spliterator(source.stream().mapToInt(i->i).toArray());

                    return new StreamSpliterators.SliceSpliterator.OfInt(s, skip, limit);
                });
            };
            data.add(new Object[]{"StreamSpliterators.SliceSpliterator.OfInt", r});
        }

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Long> source =  LongStream.range(0, size).boxed().collect(toList());

                SpliteratorTestHelper.testLongSpliterator(() -> {
                    Spliterator.OfLong s = Arrays.spliterator(source.stream().mapToLong(i->i).toArray());

                    return new StreamSpliterators.SliceSpliterator.OfLong(s, skip, limit);
                });
            };
            data.add(new Object[]{"StreamSpliterators.SliceSpliterator.OfLong", r});
        }

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Double> source =  LongStream.range(0, size).asDoubleStream().boxed().collect(toList());

                SpliteratorTestHelper.testDoubleSpliterator(() -> {
                    Spliterator.OfDouble s = Arrays.spliterator(source.stream().mapToDouble(i->i).toArray());

                    return new StreamSpliterators.SliceSpliterator.OfDouble(s, skip, limit);
                });
            };
            data.add(new Object[]{"StreamSpliterators.SliceSpliterator.OfLong", r});
        }


        // Unordered slice spliterator

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Integer> source =  IntStream.range(0, size).boxed().collect(toList());
                final UnorderedContentAsserter<Integer> uca = new UnorderedContentAsserter<>(source);

                SpliteratorTestHelper.testSpliterator(() -> {
                    Spliterator<Integer> s = Arrays.spliterator(source.stream().toArray(Integer[]::new));

                    return new StreamSpliterators.UnorderedSliceSpliterator.OfRef<>(s, skip, limit);
                }, uca);
            };
            data.add(new Object[]{"StreamSpliterators.UnorderedSliceSpliterator.OfRef", r});
        }

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Integer> source =  IntStream.range(0, size).boxed().collect(toList());
                final UnorderedContentAsserter<Integer> uca = new UnorderedContentAsserter<>(source);

                SpliteratorTestHelper.testIntSpliterator(() -> {
                    Spliterator.OfInt s = Arrays.spliterator(source.stream().mapToInt(i->i).toArray());

                    return new StreamSpliterators.UnorderedSliceSpliterator.OfInt(s, skip, limit);
                }, uca);
            };
            data.add(new Object[]{"StreamSpliterators.UnorderedSliceSpliterator.OfInt", r});
        }

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Long> source =  LongStream.range(0, size).boxed().collect(toList());
                final UnorderedContentAsserter<Long> uca = new UnorderedContentAsserter<>(source);

                SpliteratorTestHelper.testLongSpliterator(() -> {
                    Spliterator.OfLong s = Arrays.spliterator(source.stream().mapToLong(i->i).toArray());

                    return new StreamSpliterators.UnorderedSliceSpliterator.OfLong(s, skip, limit);
                }, uca);
            };
            data.add(new Object[]{"StreamSpliterators.UnorderedSliceSpliterator.OfLong", r});
        }

        {
            SliceTester r = (size, skip, limit) -> {
                final Collection<Double> source =  LongStream.range(0, size).asDoubleStream().boxed().collect(toList());
                final UnorderedContentAsserter<Double> uca = new UnorderedContentAsserter<>(source);

                SpliteratorTestHelper.testDoubleSpliterator(() -> {
                    Spliterator.OfDouble s = Arrays.spliterator(LongStream.range(0, SIZE).asDoubleStream().toArray());

                    return new StreamSpliterators.UnorderedSliceSpliterator.OfDouble(s, skip, limit);
                }, uca);
            };
            data.add(new Object[]{"StreamSpliterators.UnorderedSliceSpliterator.OfLong", r});
        }

        return data.toArray(new Object[0][]);
    }

    static final int SIZE = 256;

    static final int STEP = 32;

    @Test(dataProvider = "sliceSpliteratorDataProvider")
    public void testSliceSpliterator(String description, SliceTester r) {
        setContext("size", SIZE);
        for (int skip = 0; skip < SIZE; skip += STEP) {
            setContext("skip", skip);
            for (int limit = 0; limit < SIZE; limit += STEP) {
                setContext("limit", skip);
                r.test(SIZE, skip, limit);
            }
        }
    }
}
