/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Neg07 {
    interface I {
        default int m() { return 1; }
    }

    static class C1 {
        public void m() { } //incompatible return
    }

    static class C2 extends C1 implements I { }

    static class C3 implements I {
        public void m() { } //incompatible return
    }
}
