/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class TargetType43 {

    void m(Object o) { }

    void test(Object obj) {
        Object o = x-> { new NonExistentClass(x); return 5; };
        m(x-> { new NonExistentClass(x); return 5; });
    }
}
