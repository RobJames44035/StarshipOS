/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.try.not.applicable.to.type
// key: compiler.err.prob.found.req
// key: compiler.misc.inconvertible.types

class ResourceNotApplicableToType {
    void m() {
        try (String s = "") {
        }
    }
}
