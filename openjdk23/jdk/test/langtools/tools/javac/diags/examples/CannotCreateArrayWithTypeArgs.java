/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cannot.create.array.with.type.arguments

class X {
    Object[] array = new<Integer> Object[3];
}
