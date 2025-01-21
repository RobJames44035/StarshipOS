/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

interface SAM {
    void m(Integer x);
}

class Test {
    void test(Object x) {}

    void test1() {
        test((int x)-> { } + (int x)-> { } );
        test((int x)-> { } instanceof Object );
    }

    void test2() {
        int i2 = (int x)-> { } + (int x)-> { };
        boolean b = (int x)-> { } instanceof Object;
    }

    void test3() {
        test((Object)(int x)-> { });
        Object o = (Object)(int x)-> { };
    }
}
