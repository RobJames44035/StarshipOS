/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8169335
 * @summary Add a crypto policy fallback in case Security Property
 * 'crypto.policy' does not exist.
 * @run main/othervm CryptoPolicyFallback
 */
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import javax.crypto.*;

/*
 * Take the current java.security file, strip out the 'crypto.policy' entry,
 * write to a new file in the current directory, then use that file as the
 * replacement java.security file.  This test will fail if the crypto.policy
 * entry doesn't match the compiled in value.
 */
public class CryptoPolicyFallback {

    private static final String FILENAME = "java.security";

    public static void main(String[] args) throws Exception {

        String javaHome = System.getProperty("java.home");

        Path path = Paths.get(javaHome, "conf", "security", FILENAME);

        /*
         * Get the default value.
         */
        String defaultPolicy;
        try (Stream<String> lines = Files.lines(path)) {
            /*
             * If the input java.security file is malformed
             * (missing crypto.policy, attribute/no value, etc), throw
             * exception.  split() might throw AIOOB which
             * is ok behavior.
             */
            defaultPolicy = lines.filter(x -> x.startsWith("crypto.policy="))
                    .findFirst().orElseThrow(
                            () -> new Exception("Missing crypto.policy"))
                    .split("=")[1].trim();
        }

        /*
         * We know there is at least one crypto.policy entry, strip
         * all of them out of the java.security file.
         */
        try (PrintWriter out = new PrintWriter(FILENAME);
                Stream<String> lines = Files.lines(path)) {
            lines.filter(x -> !x.trim().startsWith("crypto.policy="))
                    .forEach(out::println);
        }

        /*
         * "-Djava.security.properties==file" does a complete replacement
         * of the system java.security file.  i.e. value must be "=file"
         */
        System.setProperty("java.security.properties", "=" + FILENAME);

        /*
         * Find out expected value.
         */
        int expected;
        switch (defaultPolicy) {
        case "limited":
            expected = 128;
            break;
        case "unlimited":
            expected = Integer.MAX_VALUE;
            break;
        default:
            throw new Exception(
                    "Unexpected Default Policy Value: " + defaultPolicy);
        }

        /*
         * Do the actual check.  If the JCE Framework can't initialize
         * an Exception is normally thrown here.
         */
        int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");

        System.out.println("Default Policy: " + defaultPolicy
                + "\nExpected max AES key length: " + expected
                + ", received : " + maxKeyLen);

        if (expected != maxKeyLen) {
            throw new Exception("Wrong Key Length size!");
        }

        System.out.println("PASSED!");
    }
}
