/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4029427
   @summary Make sure ZipInputStream.skip(n) will check
            for negative n.
   */


import java.io.*;
import java.util.zip.*;

public class Skip {
    public static void main(String[] args) throws Exception {
        ZipInputStream z = new ZipInputStream(System.in);

        try {
            z.skip(-1);
            throw new Exception("Skip allowed negative value");
        } catch (IllegalArgumentException e) {
        }
    }
}
