/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8046085
 * @summary Ensure that when trees are being used for collisions that null key
 * insertion still works.
 */

import java.util.*;
import java.util.stream.IntStream;

public class PutNullKey {

    // Initial capacity of map
    // Should be >= the map capacity for treeifying, see HashMap/ConcurrentMap.MIN_TREEIFY_CAPACITY
    static final int INITIAL_CAPACITY = 64;

    // Maximum size of map
    // Should be > the treeify threshold, see HashMap/ConcurrentMap.TREEIFY_THRESHOLD
    static final int SIZE = 256;

    // Load factor of map
    // A value 1.0 will ensure that a new threshold == capacity
    static final float LOAD_FACTOR = 1.0f;

    public static class CollidingHash implements Comparable<CollidingHash> {

        private final int value;

        public CollidingHash(int value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            // intentionally bad hashcode. Force into first bin.
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (null == o) {
                return false;
            }

            if (o.getClass() != CollidingHash.class) {
                return false;
            }

            return value == ((CollidingHash) o).value;
        }

        @Override
        public int compareTo(CollidingHash o) {
            return value - o.value;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<Object,Object> m = new HashMap<>(INITIAL_CAPACITY, LOAD_FACTOR);
        IntStream.range(0, SIZE)
                .mapToObj(CollidingHash::new)
                .forEach(e -> { m.put(e, e); });

        // kaboom?
        m.put(null, null);
    }
}
