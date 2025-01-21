/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class MethodReference53 {

    interface SAM1 {
        void m(int i);
    }

    interface SAM2 {
        void m(long i);
    }

    void m(SAM1 s1) { }
    void m(SAM2 s1) { }

    void test() {
        m(this::unknown); //should not generate outer resolution diagnostic
    }
}
