/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * LambdaTranslationCompoundSamTest
 *
 * @author Brian Goetz
 */
@Test
public class LambdaTranslationCompoundSamTest {
    interface Accepts<T> {
        void accept(T t);
    }

    interface AcceptsInt {
        void accept(int i);
    }

    interface A<T> extends Accepts<T> {

        default void accept(int value) {
            throw new IllegalStateException();
        }

        interface OfInt extends A<Integer>, AcceptsInt {
            @Override
            void accept(int value);

            @Override
            default void accept(Integer i) {
                accept(i.intValue());
            }
        }
    }

    protected interface Target<T> extends A<T> {
        public interface OfInt extends Target<Integer>, A.OfInt { }
    }

    public void testConversion() {
        int[] result = new int[4];

        Target<Integer> tb = (Integer i) -> { result[0] = i+1; };
        tb.accept((Integer) 3);
        assertEquals(4, result[0]);

        Target.OfInt ti = (int i) -> { result[1] = i+1; };
        ti.accept(7);
        assertEquals(8, result[1]);
    }
}
