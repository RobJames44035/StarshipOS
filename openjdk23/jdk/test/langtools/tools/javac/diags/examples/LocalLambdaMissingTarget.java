/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// key: compiler.err.cant.infer.local.var.type
// key: compiler.misc.local.lambda.missing.target

class LocalLambdaMissingTarget {
    void test() {
        var x = () -> { };
    }
}
