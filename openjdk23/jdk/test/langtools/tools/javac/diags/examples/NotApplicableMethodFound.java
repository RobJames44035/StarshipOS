/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.misc.not.applicable.method.found
// key: compiler.note.verbose.resolve.multi.1
// key: compiler.err.cant.apply.symbol
// key: compiler.misc.no.conforming.assignment.exists
// key: compiler.misc.inconvertible.types
// options: --debug=verboseResolution=inapplicable,failure

class NotApplicableMethodFound {

    void m(int i) {}

    { m(""); }
}
