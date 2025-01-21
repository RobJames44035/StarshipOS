/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8255968
 * @summary Confusing error message for inaccessible constructor
 * @run compile -XDrawDiagnostics T8255968_8.java
 */

class T8255968_8_Outer {
    void m() {}
    void m(String s) {}

    class T8255968_8_Inner extends T8255968_8_Sup {
        void test() {
            m();
        }
    }
}

class T8255968_8_Sup {
    private void m(String s) {}
    private void m() {}
}
