/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// key: compiler.misc.diamond
// key: compiler.err.cant.apply.diamond.1
// key: compiler.misc.diamond.invalid.arg
// key: compiler.misc.diamond.invalid.args

import java.util.*;

class DiamondAndNonDenotableType<T> {
    DiamondAndNonDenotableType(T t) {}
}

class DiamondAndNonDenotableTypes<T, S> {
    DiamondAndNonDenotableTypes(T t, S s) {}
    void m() {
        List<?> wl = null;
        new DiamondAndNonDenotableTypes<>(wl, wl) {};
        new DiamondAndNonDenotableType<>(wl) {};
    };
}
