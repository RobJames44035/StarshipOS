/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test %E
 * @bug 4398553
 * @summary The compiler sometimes incorrectly reused bits when computing DA/DU
 * @author Neal Gafter (gafter)
 *
 * @run compile/fail DABlock.java
 */

class DABlock {
    void foo() {
        try {
            String y = "yyy";
        } finally {
        }
        String a = String.valueOf(a);
        System.out.println("a=" + a);
    }
}
