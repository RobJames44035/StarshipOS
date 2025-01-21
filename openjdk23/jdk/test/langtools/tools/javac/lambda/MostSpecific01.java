/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Test {

    interface IntMapper {
        int map();
    }

    interface LongMapper {
        long map();
    }

    void m(IntMapper im, String s) { }
    void m(LongMapper lm, Integer s) { }

    void test() {
        m(()->1, null);
    }
}
