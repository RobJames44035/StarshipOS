/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4708676
 * @summary Compiler assertion failure with forward reference
 * @author Neal Gafter (gafter)
 *
 * @compile DUBeforeDefined1.java
 * @run main DUBeforeDefined1
 */

public class DUBeforeDefined1 {
    static int i = j = 1;
    static final int j;
    public static void main(String[] args) {
        if (i != j) throw new Error();
    }
}
