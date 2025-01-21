/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4174691
   @summary Test if the constructor will check for null input.
   */


import java.util.zip.ZipInputStream;

public class Constructor {
    public static void main(String[] args) throws Exception {
        try {
            ZipInputStream in = new ZipInputStream(null);
            throw new Exception("Constructor did not check the null argument");
        } catch (NullPointerException e) {
        }
    }
}
