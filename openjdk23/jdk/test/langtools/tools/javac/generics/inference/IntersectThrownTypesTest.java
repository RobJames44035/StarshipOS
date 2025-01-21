/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class IntersectThrownTypesTest {

    interface S1 {
        <K extends Exception> void run(Class<K> clazz) throws K;
    }

    interface S2 {
        <K extends Exception> void run(Class<K> clazz) throws K;
    }

    interface S extends S1, S2 {}

    public void foo(S1 s) {
        s.run(java.io.IOException.class);
    }

    public void foo(S s) {
        s.run(java.io.IOException.class);
    }

}
