/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.unexpected.lambda

class UnexpectedLambda {
    { (()-> { })++; }
}
