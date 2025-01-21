/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.inconvertible.types
// key: compiler.misc.incompatible.ret.type.in.mref

class IncompatibleRetTypeInMref {
    interface SAM {
        Integer m();
    }

    static String f() { }

    SAM s = IncompatibleRetTypeInMref::f;
}
