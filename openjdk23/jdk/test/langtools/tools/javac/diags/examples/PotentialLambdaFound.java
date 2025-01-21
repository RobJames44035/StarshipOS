/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.warn.potential.lambda.found
// options: -XDfind=lambda

class PotentialLambdaFound {

    interface SAM {
        void m();
    }

    SAM s = new SAM() { public void m() { } };
}
