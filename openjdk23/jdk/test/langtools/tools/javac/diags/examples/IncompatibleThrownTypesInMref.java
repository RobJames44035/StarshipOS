/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.incompatible.thrown.types.in.mref

class IncompatibleThrownTypesInMref {
    interface SAM {
        void m();
    }

    static void f() throws Exception { }

    SAM s = IncompatibleThrownTypesInMref::f;
}
