/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
 * @bug 4429369
 * @summary  ISO2022CN and ISO2022KR converters throw exception
 * @modules jdk.charsets
 */

import java.io.*;
import java.nio.charset.*;

public class TestIllegalISO2022Esc {

    public static void main ( String[] args) throws Exception {
        int exceptionCount = 0;
        String[] encName = {"ISO2022CN", "ISO2022KR" };
        byte[]b= {
                (byte)0x1b, //Illegal sequence for both converters.
                (byte)')',
                (byte)'x'
        };

        for ( int i=0; i < 2; i++) { // Test 2 converters.
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                    InputStreamReader isr =
                                new InputStreamReader(bais,encName[i]);
                    char cc[] = new char[1];
                    isr.read(cc,0,1); //attempt to read
            } catch (MalformedInputException e) { } // Passes if thrown
              catch (Throwable t) {
                    System.err.println("error with converter " + encName[i]);
                    exceptionCount++;
            }
        }

        if (exceptionCount > 0)
           throw new Exception ("Incorrect handling of illegal ISO2022 escapes");
    }
}
