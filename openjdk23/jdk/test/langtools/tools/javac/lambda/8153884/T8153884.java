/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T8153884 {
    void test() {
        Runnable r = () -> (foo());
    }

    void foo() { }
}
