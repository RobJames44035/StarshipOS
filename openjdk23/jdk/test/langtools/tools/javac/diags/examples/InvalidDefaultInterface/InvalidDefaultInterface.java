/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.misc.invalid.default.interface
// key: compiler.misc.bad.class.file.header
// key: compiler.err.cant.access
// options: -processor CreateBadClassFile

/* The annotation processor will create an invalid classfile with version 51.0
 * and a non-abstract method in an interface. Loading the classfile will produce
 * the diagnostic.
 */
class InvalidDefaultInterface { }
