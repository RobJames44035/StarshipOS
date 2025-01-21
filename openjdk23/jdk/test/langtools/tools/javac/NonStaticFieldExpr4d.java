/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

class NonStaticFieldExpr4d extends p1.NonStaticFieldExpr4c {
    // since a.i is an instance field, and qualifier is not d or subclass,
    // only b.i is accessible
    int i = p1.NonStaticFieldExpr4c.i;
}
