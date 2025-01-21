/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.misc.feature.sealed.classes
// key: compiler.err.feature.not.supported.in.source.plural
// options: --release 16

sealed class Sealed {}

final class C extends Sealed {}
