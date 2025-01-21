/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Test implements AutoCloseable {
    void test() {
        try(Test t = null) {}
    }
}
