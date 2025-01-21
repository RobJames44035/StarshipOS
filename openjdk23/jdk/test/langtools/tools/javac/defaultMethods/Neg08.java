/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class Neg08 {
    interface I {
        default void m() { }
    }

    static class C1 {
        void m() { } //weaker modifier
    }

    static class C2 extends C1 implements I { }

    static class C3 implements I {
        void m() { } //weaker modifier
    }
}
