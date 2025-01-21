/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.invalid.permits.clause
// key: compiler.misc.is.duplicated

sealed class Sealed permits Sub, Sub {}

final class Sub extends Sealed {}
