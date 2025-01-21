/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.incompatible.type.in.conditional
// key: compiler.misc.inconvertible.types

class IncompatibleTypesInConditional {

    interface A { }
    interface B { }

    B b = true ? (A)null : (B)null;
}
