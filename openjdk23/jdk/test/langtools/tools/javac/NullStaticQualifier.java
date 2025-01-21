/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4449316 4655091
 * @summary missing null pointer check in discarded subexpression
 * @author gafter
 *
 * @compile NullStaticQualifier.java
 * @run main NullStaticQualifier
 */

public class NullStaticQualifier {
    static NullStaticQualifier s = null;
    NullStaticQualifier i;
    public static void main(String[] args) {
        try {
            NullStaticQualifier nulls = null;
            nulls.s = null;
            System.out.println("ok 1 of 2");
        } catch (NullPointerException e) {
            throw new Error("failed");
        }
        try {
            s.i.s = null;
            throw new Error("failed");
        } catch (NullPointerException e) {
            System.out.println("ok 2 of 2");
        }
    }
}
