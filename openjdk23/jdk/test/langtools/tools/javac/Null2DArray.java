/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4368664
 * @summary VerifyError when using array of arrays
 * @author gafter
 *
 * @compile Null2DArray.java
 * @run main Null2DArray
 */

public class Null2DArray {
    public static void main(final String[] args) {
        try {
            float[][] channels = null;
            channels[0] = new float[10];
        } catch (NullPointerException ex) {
            return;
        }
        throw new Error();
    }
}
