/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// key: compiler.err.invalid.permits.clause
// key: compiler.misc.must.not.be.supertype

interface I {}

sealed class C implements I permits I {}
