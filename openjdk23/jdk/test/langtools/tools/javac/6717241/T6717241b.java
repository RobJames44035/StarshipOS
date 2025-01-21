/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T6717241b {
    void test() {
        //this will generate a 'cant.resolve.location'
        Object o = v;
        //this will generate a 'cant.resolve.location.args'
        m1(1, "");
        //this will generate a 'cant.resolve.location.args.params'
        T6717241b.<Integer,Double>m2(1, "");
    }
}
