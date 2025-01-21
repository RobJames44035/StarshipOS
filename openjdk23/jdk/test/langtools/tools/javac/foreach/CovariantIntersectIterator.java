/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8295020
 * @summary javac emits incorrect code for for-each on an intersection type.
 * @run main CovariantIntersectIterator
 */

import java.io.Serializable;
import java.util.Iterator;

public class CovariantIntersectIterator {

    public static void main(String... args) {
        int npeCount = 0;
        try {
            // JCEnhancedForLoop.expr's erased type is ALREADY an Iterable
            // iterator() comes from expr's erased type (MyIterable) and
            // is called using invokevirtual & returns a covariant type (MyIterable.MyIterator)
            for (Object s : (MyIterable & Serializable) null) {}
        } catch (NullPointerException e) {
            npeCount++;
        }
        try {
            // JCEnhancedForLoop.expr's erased type is NOT an Iterable
            // iterator() comes from Iterable (expr's erased type casted),
            // will be called by invokeinterface and return Iterator
            for (Object s : (MyIterableBase & Iterable<Object>) null) {}
        } catch (NullPointerException e) {
            npeCount++;
        }
        if (npeCount != 2) {
            throw new AssertionError("Expected NPE missing");
        }
    }

    abstract static class MyIterableBase {
        public abstract MyIterable.MyIterator iterator();
    }

    static class MyIterable extends MyIterableBase implements Iterable<Object> {

        class MyIterator implements Iterator<Object> {

            public boolean hasNext() {
                return false;
            }

            public Object next() {
                return null;
            }

            public void remove() {}
        }

        public MyIterator iterator() {
            return new MyIterator();
        }
    }
}
