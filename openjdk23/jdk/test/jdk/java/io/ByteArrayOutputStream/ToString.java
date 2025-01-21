/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @bug 1226190
   @summary Heartbeat test of ByteArrayOutputStream's toString methods
 */

import java.io.*;

public class ToString {

    public static void main(String[] args) throws IOException {
        String test = "This is a test.";
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(b);
        p.print(test);
        p.close();

        if (! b.toString().equals(test))
            throw new RuntimeException("Default encoding failed");
        if (! b.toString("UTF8").equals(test))
            throw new RuntimeException("UTF8 encoding failed");
        if (! b.toString(0).equals(test))
            throw new RuntimeException("Hibyte0 encoding failed");
    }

}
