/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4866847 7152564 7155693
 * @summary various CodeSource.implies tests
 */

import java.security.CodeSource;
import java.net.URL;

public class Implies {

    public static void main(String[] args) throws Exception {
        URL thisURL = new URL("http", "localhost", "file");
        URL thatURL = new URL("http", null, "file");
        // should not throw NullPointerException
        testImplies(thisURL, thatURL, false);

        thisURL = new URL("http", "localhost", "dir/-");
        thatURL = new URL("HTTP", "localhost", "dir/file");
        // protocol check should ignore case
        testImplies(thisURL, thatURL, true);

        thisURL = new URL("http", "localhost", 80, "dir/-");
        thatURL = new URL("HTTP", "localhost", "dir/file");
        // port check should match default port of thatURL
        testImplies(thisURL, thatURL, true);

        System.out.println("test passed");
    }

    private static void testImplies(URL thisURL, URL thatURL, boolean result)
            throws SecurityException {
        CodeSource thisCs
                = new CodeSource(thisURL, (java.security.cert.Certificate[]) null);
        CodeSource thatCs
                = new CodeSource(thatURL, (java.security.cert.Certificate[]) null);
        if (thisCs.implies(thatCs) != result) {
            throw new SecurityException("CodeSource.implies() returned "
                    + !result + " instead of " + result);
        }
        if (thisCs.getCodeSigners() != null && thatCs.getCodeSigners() != null) {
            throw new SecurityException("Both getCodeSigners should be null");
        }
    }
}
