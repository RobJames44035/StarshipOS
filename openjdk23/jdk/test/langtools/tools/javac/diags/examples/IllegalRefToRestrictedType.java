/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// key: compiler.warn.illegal.ref.to.restricted.type
// key: compiler.warn.restricted.type.not.allowed
// options: -Xlint:-options -source 13

class IllegalRefToVarType {
    yield list() { return null; }
    public class yield {}
}
