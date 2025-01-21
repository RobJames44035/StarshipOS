/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.misc.applicable.method.found.1
// key: compiler.note.verbose.resolve.multi
// key: compiler.misc.partial.inst.sig
// options: --debug=verboseResolution=applicable,success

class PartialInstSig {

    <X> X m() { return null; }

    { m(); }
}
