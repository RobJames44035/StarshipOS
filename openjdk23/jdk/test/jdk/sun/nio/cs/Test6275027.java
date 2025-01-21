/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
   @bug 6275027
   @summary Check if StreamEncoder works correctly when fed with unpaired
            surrogates.
 */

import java.io.*;
public class Test6275027 {
    public static void main( String arg[] ) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ps.print("\uda00");
        ps.print("\uda01");
        ps.close();
        if (!"??".equals(baos.toString()))
            throw new Exception("failed");
    }

}
