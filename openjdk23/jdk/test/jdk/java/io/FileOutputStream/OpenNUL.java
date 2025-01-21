/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8285445
 * @requires (os.family == "windows")
 * @summary Verify behavior of opening "NUL:" with ADS enabled and disabled.
 * @run main/othervm OpenNUL
 * @run main/othervm -Djdk.io.File.enableADS OpenNUL
 * @run main/othervm -Djdk.io.File.enableADS=FalsE OpenNUL
 * @run main/othervm -Djdk.io.File.enableADS=true OpenNUL
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OpenNUL {
    public static void main(String args[]) throws IOException {
        String enableADS = System.getProperty("jdk.io.File.enableADS", "true");
        boolean fails = enableADS.equalsIgnoreCase(Boolean.FALSE.toString());

        FileOutputStream fos;
        try {
            fos = new FileOutputStream("NUL:");
            if (fails)
                throw new RuntimeException("Should have failed");
        } catch (FileNotFoundException fnfe) {
            if (!fails)
                throw new RuntimeException("Should not have failed");
        }
    }
}
