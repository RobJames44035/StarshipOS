/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

public class Pattern10 {

    @SuppressWarnings("rawtypes")
    private static void troublesCausingMethod(Object arg) {
        boolean b = true;
        if (b) {
            var v = new byte[0];
        } else {
            var v = new int[0];
        }
    }
}
