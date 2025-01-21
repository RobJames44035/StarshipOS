/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class InaccessibleMref01 {
    interface SAM {
        void m();
    }

    void test(p1.C c) {
        SAM s = c::m;
    }
}
