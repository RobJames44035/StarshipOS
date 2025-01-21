/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

interface I {
    void throwCheckedException ();
}

class A {
    public void throwCheckedException () throws Throwable {
        throw new Throwable();
    }
}

class B extends A implements I {
}

public class UncaughtException {
}
