/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8235332 8248226
 * @summary Test cloning with more than 8 (=ArrayCopyLoadStoreMaxElem) fields with StressGCM
 * @library /
 * @requires vm.compiler2.enabled | vm.graal.enabled
 *
 * @run main/othervm -Xbatch
 *                   -XX:CompileCommand=dontinline,compiler.arraycopy.TestCloneAccessStressGCM::test
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM -XX:-ReduceInitialCardMarks
 *                   compiler.arraycopy.TestCloneAccessStressGCM
 * @run main/othervm -Xbatch
 *                   -XX:CompileCommand=dontinline,compiler.arraycopy.TestCloneAccessStressGCM::test
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM -XX:-ReduceInitialCardMarks
 *                   -XX:-ReduceBulkZeroing
 *                   compiler.arraycopy.TestCloneAccessStressGCM
 */

package compiler.arraycopy;

public class TestCloneAccessStressGCM {

    static int test(E src) throws CloneNotSupportedException {
        // ArrayCopyNode for this clone is not inlined since there are more than 8 (=ArrayCopyLoadStoreMaxElem) fields
        E dest = (E)src.clone();

        // The ArrayCopyNode initialization for the clone is executed after the LoadI nodes for 'dest' due to a
        // memory input from the uninitialized new object instead of the ArrayCopyNode. As a result, uninitialized
        // memory is read for each field and added together.
        return dest.i1 + dest.i2 + dest.i3 + dest.i4 + dest.i5 +
            dest.i6 + dest.i7 + dest.i8 + dest.i9;
    }

    public static void main(String[] args) throws Exception {
        TestCloneAccessStressGCM test = new TestCloneAccessStressGCM();
        int result = 0;
        E e = new E();
        for (int i = 0; i < 20000; i++) {
            result = test(e);
            if (result != 36) {
              throw new RuntimeException("Return value not 36. Got: " + result + "; additional check:  " + e.sum());
            }
        }

        if (result != 36) {
            throw new RuntimeException("Return value not 36. Got: " + result + "; additional check:  " + e.sum());
        }
    }
}

class E implements Cloneable {
    /*
     * Need more than 8 (=ArrayCopyLoadStoreMaxElem) fields
     */
    int i1;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    int i9;

    E() {
        i1 = 0;
        i2 = 1;
        i3 = 2;
        i4 = 3;
        i5 = 4;
        i6 = 5;
        i7 = 6;
        i8 = 7;
        i9 = 8;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int sum() {
        return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
    }
}

