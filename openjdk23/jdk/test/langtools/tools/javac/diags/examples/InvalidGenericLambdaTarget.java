/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.invalid.generic.lambda.target

class InvalidGenericLambdaTarget {

    interface SAM {
        <Z> void m();
    }

    SAM s = x-> { };
}
