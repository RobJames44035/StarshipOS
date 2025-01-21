/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.incompatible.arg.types.in.lambda

class IncompatibleArgTypesInLambda {
    interface SAM {
        void m(Integer x);
    }

    SAM s = (String x)-> {};
}
