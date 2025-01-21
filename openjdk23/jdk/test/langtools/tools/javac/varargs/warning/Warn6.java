/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8069254
 * @summary Ensure the generic array creation warning is not incorrectly produced for diamonds
 * @compile -Xlint:unchecked -Werror Warn6.java
 */

public class Warn6<T> {
    @SafeVarargs
    public Warn6(T... args) {
    }

    public static void main(String[] args) {
        Iterable<String> i = null;

        Warn6<Iterable<String>> foo2 = new Warn6<>(i, i);
        Warn6<Iterable<String>> foo3 = new Warn6<Iterable<String>>(i, i);
    }
}

