/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4721003
 * @summary anonymous class in explicit constructor invocation can refer implicitly to encl
 *
 * @compile WhichImplicitThis9.java
 * @run main WhichImplicitThis9
 */

public class WhichImplicitThis9 {
    static int result;
    public synchronized static void main(String[] args) {
        result = 0;
        new WhichImplicitThis9(1);
        if (result != 13658) throw new Error("" + result);
    }
    WhichImplicitThis9(final int i) {
        class L {
            L() {
                result = result*10 + 1;
            }
            L(final int j) {
                this(new L() {
                        { result = result*10 + 2 + i; }
                });
                result = result*10 + 4 + i;
            }
            L(Object o) {
                result = result*10 + 6;
            }
        }
        new L(i) {
            {
                result = result*10 + 7 + i;
            }
        };
    }
}
