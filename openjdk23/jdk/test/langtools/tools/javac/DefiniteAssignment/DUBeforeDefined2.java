/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4708676
 * @summary Compiler assertion failure with forward reference
 * @author Neal Gafter (gafter)
 *
 * @compile DUBeforeDefined2.java
 * @run main DUBeforeDefined2
 */

public class DUBeforeDefined2 {
    int i = j = 1;
    final int j;
    void f() {
        if (i != j) throw new Error();
    }
    public static void main(String[] args) {
        new DUBeforeDefined2().f();
    }
}
