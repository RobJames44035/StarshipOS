/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.static.not.qualified.by.type
// key: compiler.warn.static.not.qualified.by.type2
// options: -Xlint:static

class StaticNotQualifiedByType {
    int m(Other other) {
        return other.i;
    }

    void m2() {
        var obj = new Object() {
            static void foo() {}
        };
        obj.foo();
    }
}

class Other {
    static int i;
}
