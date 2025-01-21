/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8033421
 * @summary Check that \\@SuppressWarnings works properly when overriding deprecated method.
 * @compile -Werror -Xlint:deprecation OverriddenSuppressed.java
 */

public class OverriddenSuppressed implements Interface {
    @SuppressWarnings("deprecation")
    public void test() { }
}

interface Interface {
    @Deprecated void test();
}
