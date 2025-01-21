/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4149941
   @summary mark and reset should throw an exception even when the
   underlying stream supports the operations.

*/


import java.io.*;

public class MarkReset {

    public static void main(String args[]) throws Exception {
        CharArrayReader car = new CharArrayReader(new char[32]);
        PushbackReader pb = new PushbackReader(car);
        boolean markResult = testMark(pb);
        boolean resetResult = testReset(pb);
        if ((!markResult) || (!resetResult))
            throw new Exception("Mark and reset should not be supported");
    }

    static boolean testMark(PushbackReader pb){
        try{
            pb.mark(100);
        } catch (IOException e) {
            return true;            //  Passed the test successfully
        }
        System.err.println("Mark error");
        return false;           // Failed
    }

    static boolean testReset(PushbackReader pb){
        try{
            pb.reset();
        } catch (IOException e) {
            return true;            //  Passed the test successfully
        }
        System.err.println("Reset error");
        return false;           // Failed
    }

}
