/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.not.a.functional.intf.1
// key: compiler.misc.incompatible.abstracts

class IncompatibleAbstracts {

    interface SAM {
        void m(String s);
        void m(Integer i);
    }

    SAM s = x-> { };
}
