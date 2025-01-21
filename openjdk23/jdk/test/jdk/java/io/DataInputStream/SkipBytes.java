/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @bug 4085938
   @summary Check for the correct behaviour of DataInputStream.skipBytes
   */

import java.io.*;

public class SkipBytes{

    private static void dotest(DataInputStream dis, int pos, int total,
                               int toskip, int expected) {

        try {
            System.err.println("\n\nTotal bytes in the stream = " + total);
            System.err.println("Currently at position = " + pos);
            System.err.println("Bytes to skip = " + toskip);
            System.err.println("Expected result = " + expected);
            int skipped = dis.skipBytes(toskip);
            System.err.println("Actual skipped = " + skipped);
            if (skipped != expected) {
                throw new RuntimeException
                    ("DataInputStream.skipBytes does not return expected value");
            }
        } catch(EOFException e){
            throw new RuntimeException
                ("DataInputStream.skipBytes throws unexpected EOFException");
        } catch (IOException e) {
            System.err.println("IOException is thrown - possible result");
        }


    }



    public static void main(String args[]) throws Exception {

        DataInputStream dis = new DataInputStream(new MyInputStream());
        dotest(dis, 0, 11, -1, 0);
        dotest(dis, 0, 11, 5, 5);
        System.err.println("\n***CAUTION**** - may go into an infinite loop");
        dotest(dis, 5, 11, 20, 6);
    }
}


class MyInputStream extends InputStream {

    private int readctr = 0;

    public int read() {

        if (readctr > 10){
            return -1;
        }
        else{
            readctr++;
            return 0;
        }

    }

    public int available() { return 0; }
}
