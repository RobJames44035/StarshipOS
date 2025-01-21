/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class CaptureLowerBoundArray {

    interface I<T> {
        T[] getArray();
    }

    <T> T[] m(T[] arg) { return null; }

    void test(I<? extends Exception> i) {
        m(i.getArray())[0] = new Exception();
    }


}
