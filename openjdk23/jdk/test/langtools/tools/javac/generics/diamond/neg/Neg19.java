/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */



class Neg19 {
    public static void meth() {
        new Neg19_01<Neg19>().foo(new Neg19_01<>()); // OK.
        new Neg19_01<Neg19>().foo(new Neg19_01<>() {}); // ERROR.
    }
}

class Neg19_01<T> {
    private class Private {}
    Neg19_01() {}
    void foo(Neg19_01<Private> p) {}
}
