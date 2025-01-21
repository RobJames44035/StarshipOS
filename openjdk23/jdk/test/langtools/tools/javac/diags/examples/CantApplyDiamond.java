/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cant.apply.diamond
// key: compiler.misc.diamond

class CantApplyDiamond<T extends Number> {
    CantApplyDiamond(T t) { }
    <U> CantApplyDiamond(T t, U u) { }

    void m() {
        CantApplyDiamond<?> x = new CantApplyDiamond<>("");
    }
}
