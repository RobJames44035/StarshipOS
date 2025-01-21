/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.type.var.cant.be.deref

class Base<T> { }

class TypeVarCantBeDeref<T> extends Base<T.foo> {}
