/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary load/store elimination will print out instructions without bcis.
 * @bug 8235383
 * @requires vm.debug == true & vm.compiler1.enabled
 * @run main/othervm -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xcomp -XX:+PrintIRDuringConstruction -XX:+Verbose compiler.c1.TestPrintIRDuringConstruction
 */

package compiler.c1;

public class TestPrintIRDuringConstruction {
    static class Dummy {
        public int value;
    }

    static int foo() {
        Dummy obj = new Dummy();       // c1 doesn't have Escape Analysis

        obj.value = 0;                 // dummy store an initial value.
        return obj.value + obj.value;  // redundant load
    }

    public static void main(String[] args) {
        for (int i=0; i<5_000; ++i) {
            foo();
        }
    }
 }
