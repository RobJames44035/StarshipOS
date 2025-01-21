/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8046685 8142948
 * @summary Uncompilable large expressions involving generics.
 * @compile T8046685.java
 */
class T8046685 {

    interface Predicate<T, U> {
        public boolean apply(T t, U u);
        public boolean equals(Object o);
    }

    static <X1, X2> Predicate<X1, X2> and(final Predicate<? super X1, ? super X2> first, final Predicate<? super X1, ? super X2> second) {
        return null;
    }

    public static void test(Predicate<Integer, Integer> even) {
        and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even,
                and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even,
                and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even,
                and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, and(even, even)
                ))))))))))))))))))))))))))))))))))))))))))))))));
    }
}
