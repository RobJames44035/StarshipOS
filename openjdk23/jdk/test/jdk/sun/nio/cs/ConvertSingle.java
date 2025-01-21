/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
   @bug 4094987
   @summary Verify that malformed expression exceptions are thrown
       but no internal errors in certain pathologial cases.

 */


import java.io.*;
import java.nio.charset.*;

public class ConvertSingle {

    public static void main(String args[]) throws Exception {
        // This conversion is pathologically bad - it is attempting to
        // read unicode from an ascii encoded string.
        // The orignal bug: A internal error in ISR results if the
        // byte counter in ByteToCharUnicode
        // is not advanced as the input is consumed.

        try{
            String s = "\n";
            byte ss[] = null;
            String sstring = "x";
            ss = s.getBytes();
            ByteArrayInputStream BAIS = new ByteArrayInputStream(ss);
            InputStreamReader ISR = new InputStreamReader(BAIS, "Unicode");
            BufferedReader BR = new BufferedReader(ISR);
            sstring = BR.readLine();
            BR.close();
            System.out.println(sstring);
        } catch (MalformedInputException e){
            // Right error
            return;
        } catch (java.lang.InternalError e) {
            throw new Exception("ByteToCharUnicode is failing incorrectly for "
                                + " single byte input");
        }

    }

}
