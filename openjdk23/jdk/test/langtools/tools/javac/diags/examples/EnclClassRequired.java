/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.encl.class.required

class Base {
    class Nested { }
}

class EnclClassRequired extends Base.Nested { }
