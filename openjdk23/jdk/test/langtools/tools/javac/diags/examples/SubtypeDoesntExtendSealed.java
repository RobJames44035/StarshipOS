/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.invalid.permits.clause
// key: compiler.misc.doesnt.extend.sealed

sealed class Sealed permits Sub {}

final class Sub {}
