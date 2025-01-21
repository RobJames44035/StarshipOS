/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.anonymous.class
// key: compiler.err.prob.found.req
// key: compiler.misc.inconvertible.types
// options: -Xlint:rawtypes
// run: simple

import java.util.ArrayList;

class AnonymousClass {
     Object m() {
         return (ArrayList<String>) new ArrayList<Object>() { };
    }
}
