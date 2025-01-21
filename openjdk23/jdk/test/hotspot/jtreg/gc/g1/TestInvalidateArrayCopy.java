/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestInvalidateArrayCopy
 * @bug 8182050
 * @summary Check that benign (0-sized) out of heap bounds card table invalidations do not assert.
 * @requires vm.gc.G1
 * @requires vm.debug
 * @run main/othervm -XX:NewSize=1M -Xlog:gc -XX:MaxNewSize=1m -XX:-UseTLAB -XX:MaxHeapSize=64M gc.g1.TestInvalidateArrayCopy
 */

// The test allocates zero-sized arrays of j.l.O and tries to arraycopy random data into it so
// that the asserting post barrier calls are executed. It assumes that G1 allocates eden regions
// at the top of the heap for this problem to occur.
public class TestInvalidateArrayCopy {

    static final int NumIterations = 1000000;

    // "Random" source data to "copy" into the target.
    static Object[] sourceArray = new Object[10];

    public static void main(String[] args) {
        for (int i = 0; i < NumIterations; i++) {
            Object[] x = new Object[0];
            // Make sure that the compiler can't optimize out the above allocations.
            if (i % (NumIterations / 10) == 0) {
                System.out.println(x);
            }
            System.arraycopy(sourceArray, 0, x, 0, Math.min(x.length, sourceArray.length));
        }
    }
}
