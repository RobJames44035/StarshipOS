/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.incompatible.type.in.switch.expression
// key: compiler.misc.inconvertible.types


class IncompatibleTypesInSwitchExpression {

    interface A { }
    interface B { }

    B b = switch (0) { case 0 -> (A)null; default -> (B)null; };
}
