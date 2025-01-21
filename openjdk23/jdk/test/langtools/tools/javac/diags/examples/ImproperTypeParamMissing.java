/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.improperly.formed.type.param.missing

class ImproperTypeParamMissing {
    class Outer<S> {
        class Inner<T> {}
    }

    void m() {
        Object o = (Outer<?>.Inner)null;
    }
}
