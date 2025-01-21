/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8020842 8261969
 * @summary SNIHostName does not throw IAE when hostname doesn't conform to
 *          RFC 3490 or ends with a trailing dot
 */

import javax.net.ssl.SNIHostName;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class IllegalSNIName {

    private static void checkHostname(String hostname) throws Exception {
        try {
            new SNIHostName(hostname);
            throw new RuntimeException("Expected to get IllegalArgumentException for "
                    + hostname);
        } catch (IllegalArgumentException iae) {
            // That's the right behavior.
        }
    }

    private static void checkHostname(byte[] encodedHostname) throws Exception {
        try {
            new SNIHostName(encodedHostname);
            throw new RuntimeException("Expected to get IllegalArgumentException for "
                    + HexFormat.ofDelimiter(":").formatHex(encodedHostname));
        } catch (IllegalArgumentException iae) {
            // That's the right behavior.
        }
    }

    public static void main(String[] args) throws Exception {
        String[] illegalNames = {
                "example\u3002\u3002com",
                "example..com",
                "com\u3002",
                "com.",
                ".",
                "example^com"
        };

        for (String name : illegalNames) {
            checkHostname(name);
            checkHostname(name.getBytes(StandardCharsets.UTF_8));
        }
    }
}
