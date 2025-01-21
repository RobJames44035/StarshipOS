/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8336320
 * @summary NullPointerException: Cannot invoke Type.getTag because type is null after JDK-8334037
 * @compile MrefDoubleTrans.java
 */
class MrefDoubleTrans {
    public void f() {
        Runnable r = new I()::m;
    }

    class I {
        void m(Object... xs) {}
    }
}
