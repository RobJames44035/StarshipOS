/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4140445 4140451
   @summary Test if constructor will check for illegal
            arguments.
*/



import java.io.*;

public class Constructor {
    public static void main(String argv[]) throws Exception {
        int values[] = {Integer.MIN_VALUE, -1, 0, 1, 4, 16, 31,
                        32, 33, Integer.MAX_VALUE};
        char b[][] = {null, new char[32]};

        int i = 0, j = 0, k = 0;
        boolean nullPtr = false, indexOutBnd = false;

        for (i = 0; i < b.length; i++) {
            for ( j = 0; j < values.length; j++) {
                for ( k = 0; k < values.length; k++) {

                    nullPtr = (b[i] == null);

                    int bufLen = nullPtr ? 0 : b[i].length;
                    indexOutBnd = (values[j] < 0)
                        || (values[j] > bufLen)
                        || (values[k] < 0)
                        || ((values[j] + values[k]) < 0);

                    try {
                        CharArrayReader rdr = new CharArrayReader
                            (b[i], values[j], values[k]);
                    } catch (NullPointerException e) {
                        if (!nullPtr) {
                            throw new Exception
                                ("should not throw NullPointerException");
                        }
                        continue;
                    } catch (IllegalArgumentException e) {
                        if (!indexOutBnd) {
                            throw new Exception
                                ("should not throw IllegalArgumentException");
                        }
                        continue;
                    }

                    if (nullPtr || indexOutBnd) {
                        throw new Exception("Failed to detect illegal argument");
                    }
                }
            }
        }
    }
}
