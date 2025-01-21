/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4403168
 * @summary Verify correct type of result of ?: operator when constant folding occurs
 * @author gafter
 *
 * @run main FoldConditional
 */

public class FoldConditional {
    static void f(double x) {
        // OK
    }
    static void f(int x) {
        throw new Error();
    }
    public static void main(String args[]){
        int x=5;
        String value0 = ("value is " + 9.0).intern();
        String value1 = ("value is " + ((x> 4)?9:9.9)).intern();
        String value2 = ("value is " + ( true ? 9 : (9.9+x) )).intern();
        f(true ? 9 : 9.9);
        f(true ? 9 : (9.9 + x));
        if (value0 != value1) throw new Error();
        if (value0 != value2) throw new Error();
    }
}
