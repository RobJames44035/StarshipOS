/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4166469
   @summary Make sure JarInputStream constructor will not
            throw NullPointerException when the JAR file is
            empty.
   */

import java.util.jar.*;
import java.io.*;

public class EmptyJar {
    public static void main(String args[]) throws Exception {
        try {
            JarInputStream is = new JarInputStream
                (new ByteArrayInputStream(new byte[0]));
        } catch (NullPointerException e) {
            throw new Exception("unexpected NullPointerException");
        }
    }
}
