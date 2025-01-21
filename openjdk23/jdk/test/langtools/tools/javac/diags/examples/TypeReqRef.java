/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.type.req.ref
// key: compiler.err.type.found.req

class TypeReqRef {

    void method(Inner<int> in) {}
    class Inner<T> {}
}
