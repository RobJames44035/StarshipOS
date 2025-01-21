/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6239297
 * @summary keytool usage is broken after changing Resources.java
 * @author Max Wang
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;

public class Resource {
    public static void main(String[] args) throws Exception {
        SecurityTools.keytool()
                .shouldNotContain("MissingResourceException");
    }
}
