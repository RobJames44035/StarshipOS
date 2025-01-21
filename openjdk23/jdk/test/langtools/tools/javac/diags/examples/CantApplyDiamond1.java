/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.cant.apply.diamond.1
// key: compiler.misc.incompatible.bounds
// key: compiler.misc.eq.bounds
// key: compiler.misc.upper.bounds
// key: compiler.misc.diamond

class CantApplyDiamond1<X> {

    CantApplyDiamond1(CantApplyDiamond1<? super X> lz) { }

    void test(CantApplyDiamond1<Integer> li) {
       CantApplyDiamond1<String> ls = new CantApplyDiamond1<>(li);
    }
}
