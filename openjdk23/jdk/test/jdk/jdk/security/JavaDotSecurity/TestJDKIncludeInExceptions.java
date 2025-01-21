/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.security.Security;

/**
 * @test
 * @bug 8207846 8208691
 * @summary Test the default setting of the jdk.net.includeInExceptions
 *          security property
 * @comment In OpenJDK, this property is empty by default and on purpose.
 *          This test assures the default is not changed.
 * @run main TestJDKIncludeInExceptions
 */
public class TestJDKIncludeInExceptions {

    public static void main(String args[]) throws Exception {
        String incInExc = Security.getProperty("jdk.includeInExceptions");
        if (incInExc != null) {
            throw new RuntimeException("Test failed: default value of " +
                "jdk.includeInExceptions security property is not null: " +
                incInExc);
        }
    }
}
