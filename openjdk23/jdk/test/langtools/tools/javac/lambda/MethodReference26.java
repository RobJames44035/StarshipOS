/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @summary check strict method conversion allows loose method reference conversion
 * @compile MethodReference26.java
 */

class MethodReference26 {

    static void m(Integer i) { }

    interface SAM {
        void m(int x);
    }

    static void call(int i, SAM s) {   }
    static void call(Integer i, SAM s) {   }

    static void test() {
        call(1, MethodReference26::m); //ok
    }
}
