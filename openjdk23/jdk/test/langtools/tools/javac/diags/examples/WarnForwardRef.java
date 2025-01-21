/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.forward.ref
// options: -XDuseBeforeDeclarationWarning

class X {
    static int x = X.y;
    static int y;
}
