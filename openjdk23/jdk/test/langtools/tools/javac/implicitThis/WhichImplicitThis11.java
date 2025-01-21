/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4825093
 * @summary code involving inner classes causes verify error
 *
 * @compile WhichImplicitThis11.java
 * @run main WhichImplicitThis11
 */

public class WhichImplicitThis11 {
    public class Inner extends WhichImplicitThis11 {
        Inner(String s) {
            this();
        }
        Inner() {
        }
    }
    public static void main(String[] args) {
        new WhichImplicitThis11().new Inner("");
    }
}
