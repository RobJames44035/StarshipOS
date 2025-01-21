/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */


/* @test
   @bug 4085939
   @summary Check for the correct behaviour of LineNumberInputStream.skip
   */

import java.io.*;


public class Skip{

    private static void dotest(LineNumberInputStream in , int curpos ,
                               long total , long toskip , long expected)
        throws Exception
    {

        try {

            System.err.println("\n\nCurrently at pos = " + curpos +
                               "\nTotal bytes in the Stream = " + total +
                               "\nNumber of bytes to skip = " + toskip +
                               "\nNumber of bytes that should be skipped = " +
                               expected);

            long skipped = in.skip(toskip);

            System.err.println("actual number skipped: "+ skipped);

            if ((skipped < 0) || (skipped > expected)) {
                throw new RuntimeException("Unexpected number of bytes skipped");
            }

        } catch (IOException e) {
            System.err.println("IOException is thrown - possible result");
        } catch (Throwable e) {
            throw new RuntimeException("Unexpected "+e+" is thrown!");
        }

    }

    public static void main( String argv[] ) throws Exception {

        LineNumberInputStream in = new LineNumberInputStream(new MyInputStream(11));

        /* test for negative skip */
        dotest(in,  0, 11, -23,  0);

        /* check for skip beyond EOF starting from before EOF */
        dotest(in,  0, 11,  20, 11);

        /* check for skip after EOF */
        dotest(in, -1, 11,  20,  0);

    }

}

class MyInputStream extends InputStream {

    private int readctr = 0;
    private long endoffile;

    public MyInputStream(long endoffile) {
        this.endoffile = endoffile;
    }

    public int read() {

        if (readctr == endoffile) {
            return -1;
        }
        else {
            readctr++;
            return 0;
        }
    }

    public int available() { return 0; }
}
