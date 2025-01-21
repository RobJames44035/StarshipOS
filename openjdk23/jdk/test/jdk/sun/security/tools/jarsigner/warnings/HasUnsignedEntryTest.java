/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.util.JarUtils;

/**
 * @test
 * @bug 8024302 8026037
 * @summary Test for hasUnsignedEntry warning
 * @library /test/lib ../
 * @build jdk.test.lib.util.JarUtils
 * @run main HasUnsignedEntryTest
 */
public class HasUnsignedEntryTest extends Test {

    /**
     * The test signs and verifies a jar that contains unsigned entries
     * which have not been integrity-checked (hasUnsignedEntry).
     * Warning message is expected.
     */
    public static void main(String[] args) throws Throwable {
        HasUnsignedEntryTest test = new HasUnsignedEntryTest();
        test.start();
    }

    private void start() throws Throwable {
        System.out.println(String.format("Create a %s that contains %s",
                UNSIGNED_JARFILE, FIRST_FILE));
        Utils.createFiles(FIRST_FILE, SECOND_FILE);
        JarUtils.createJar(UNSIGNED_JARFILE, FIRST_FILE);

        // create key pair for signing
        createAlias(CA_KEY_ALIAS, "-ext", "bc:c");
        createAlias(KEY_ALIAS);
        issueCert(
                KEY_ALIAS,
                "-validity", Integer.toString(VALIDITY));

        // sign jar
        OutputAnalyzer analyzer = jarsigner(
                "-verbose",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                "-signedjar", SIGNED_JARFILE,
                UNSIGNED_JARFILE,
                KEY_ALIAS);

        checkSigning(analyzer);

        System.out.println(String.format("Copy %s to %s, and add %s.class, "
                + "so it contains unsigned entry",
                new Object[]{SIGNED_JARFILE, UPDATED_SIGNED_JARFILE,
                    SECOND_FILE}));

        JarUtils.updateJar(SIGNED_JARFILE, UPDATED_SIGNED_JARFILE, SECOND_FILE);

        // verify jar
        analyzer = jarsigner(
                "-verify",
                "-verbose",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                UPDATED_SIGNED_JARFILE);

        checkVerifying(analyzer, 0, HAS_UNSIGNED_ENTRY_VERIFYING_WARNING);

        // verify jar in strict mode
        analyzer = jarsigner(
                "-verify",
                "-verbose",
                "-strict",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                UPDATED_SIGNED_JARFILE);

        checkVerifying(analyzer, HAS_UNSIGNED_ENTRY_EXIT_CODE,
                HAS_UNSIGNED_ENTRY_VERIFYING_WARNING);

        System.out.println("Test passed");
    }

}
