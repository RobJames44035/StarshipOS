/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.dc.gt.expected
// key: compiler.note.note
// key: compiler.note.proc.messager
// run: backdoor
// options: -processor DocCommentProcessor -proc:only

class GreaterThanExpected {
    /** @param <T */
    <T> void m(T t) { }
}

