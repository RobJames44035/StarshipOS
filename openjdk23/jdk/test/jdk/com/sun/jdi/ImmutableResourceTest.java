/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug        6287579
 * @summary    SubClasses of ListResourceBundle should fix getContents()
 *
 * @modules jdk.jdi/com.sun.tools.example.debug.tty
 *
 * @compile --add-exports jdk.jdi/com.sun.tools.example.debug.tty=ALL-UNNAMED -g ImmutableResourceTest.java
 *
 * @run main/othervm --add-exports jdk.jdi/com.sun.tools.example.debug.tty=ALL-UNNAMED ImmutableResourceTest
 */

import java.util.ResourceBundle;

public class ImmutableResourceTest {

    public static void main(String[] args) throws Exception {
        /* Reach under the covers and get the message strings */
        com.sun.tools.example.debug.tty.TTYResources ttyr =
            new com.sun.tools.example.debug.tty.TTYResources ();
        Object [][] testData = ttyr.getContents();

        /* Shred our copy of the message strings */
        for (int ii = 0; ii < testData.length; ii++) {
            testData[ii][0] = "T6287579";
            testData[ii][1] = "yyy";
        }

        /*
         * Try to lookup the shredded key.
         * If this is successful we have a problem.
         */
        String ss = null;
        try {
            ss = ttyr.getString("T6287579");
        } catch (java.util.MissingResourceException mre) {
            /*
             * Ignore the expected exception since key "T6287579" is
             * not in the canonical TTYResources.
             */
        }
        if ("yyy".equals(ss)) {
            throw new Exception ("SubClasses of ListResourceBundle should fix getContents()");
        }
        System.out.println("...Finished.");
    }
}
