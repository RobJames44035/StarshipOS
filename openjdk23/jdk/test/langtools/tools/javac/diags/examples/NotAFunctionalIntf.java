/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.not.a.functional.intf

class NotAFunctionalIntf {

    abstract class SAM {
        abstract <Z> void m();
    }

    SAM s = x-> { };
}
