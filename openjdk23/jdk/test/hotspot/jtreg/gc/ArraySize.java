/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4063078
 * @summary Allocating a ridiculously large array should not crash the VM
 * @run main/othervm -Xmx128m -Xms32m gc.ArraySize
 */

package gc;

public class ArraySize {

    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        try {
            byte[] buf = new byte[Integer.MAX_VALUE - 1];
            System.out.print(buf[0]);
        } catch (OutOfMemoryError x) {
            thrown = true;
        }
        if (! thrown) {
            throw new Exception("Didn't throw expected OutOfMemoryError");
        }
    }

}
