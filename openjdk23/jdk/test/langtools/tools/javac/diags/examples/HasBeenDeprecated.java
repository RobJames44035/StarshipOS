/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.has.been.deprecated
// options: -Xlint:deprecation

package p;

class Test {
    DeprecatedClass d;
}

@Deprecated
class DeprecatedClass { }
