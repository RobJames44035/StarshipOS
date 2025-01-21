/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8255968
 * @summary Confusing error message for inaccessible constructor
 * @run compile -XDrawDiagnostics T8255968_9.java
 */

class T8255968_9 {
    T8255968_9_TestMethodReference c = T8255968_9_Test::new;
}

interface T8255968_9_TestMethodReference {
    T8255968_9_Test create(int x);
}

class T8255968_9_Test {
    T8255968_9_Test(int x) {}
}
