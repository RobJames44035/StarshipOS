/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.cant.apply.symbol
// key: compiler.misc.inconvertible.types
// key: compiler.misc.infer.no.conforming.assignment.exists

import java.util.*;

class InferNoConformingAssignment {
    <X extends Number> List<X> m(String s) { return null; }
    { this.m(1); }
}

