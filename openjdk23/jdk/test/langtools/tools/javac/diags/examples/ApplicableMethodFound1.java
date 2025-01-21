/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.misc.applicable.method.found.1
// key: compiler.note.verbose.resolve.multi
// key: compiler.misc.partial.inst.sig
// options: --debug=verboseResolution=applicable,success

class ApplicableMethodFound1 {

    <X> void m(X x) {}

    { m(1); }
}
