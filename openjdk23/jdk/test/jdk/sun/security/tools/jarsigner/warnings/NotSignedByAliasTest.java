/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.util.JarUtils;

/**
 * @test
 * @bug 8024302 8026037
 * @summary Test for notSignedByAlias warning
 * @library /test/lib ../
 * @build jdk.test.lib.util.JarUtils
 * @run main NotSignedByAliasTest
 */
public class NotSignedByAliasTest extends Test {

    /**
     * The test signs and verifies a jar that contains signed entries
     * which are not signed by the specified alias(es) (notSignedByAlias).
     * Warning message is expected.
     */
    public static void main(String[] args) throws Throwable {
        NotSignedByAliasTest test = new NotSignedByAliasTest();
        test.start();
    }

    protected void start() throws Throwable {
        // create a jar file that contains one class file
        Utils.createFiles(FIRST_FILE);
        JarUtils.createJar(UNSIGNED_JARFILE, FIRST_FILE);

        createAlias(CA_KEY_ALIAS, "-ext", "bc:c");

        // create first key pair for signing
        createAlias(FIRST_KEY_ALIAS);
        issueCert(
                FIRST_KEY_ALIAS,
                "-validity", Integer.toString(VALIDITY));

        // create first key pair for signing
        createAlias(SECOND_KEY_ALIAS);
        issueCert(
                SECOND_KEY_ALIAS,
                "-validity", Integer.toString(VALIDITY));

        // sign jar with first key
        OutputAnalyzer analyzer = jarsigner(
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                "-signedjar", SIGNED_JARFILE,
                UNSIGNED_JARFILE,
                FIRST_KEY_ALIAS);

        checkSigning(analyzer);

        // verify jar with second key
        analyzer = jarsigner(
                "-verify",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                SIGNED_JARFILE,
                SECOND_KEY_ALIAS);

        checkVerifying(analyzer, 0, NOT_SIGNED_BY_ALIAS_VERIFYING_WARNING);

        // verify jar with second key in strict mode
        analyzer = jarsigner(
                "-verify",
                "-strict",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                SIGNED_JARFILE,
                SECOND_KEY_ALIAS);

        checkVerifying(analyzer, NOT_SIGNED_BY_ALIAS_EXIT_CODE,
                NOT_SIGNED_BY_ALIAS_VERIFYING_WARNING);

        // verify jar with non-existing alias
        analyzer = jarsigner(
                "-verify",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                SIGNED_JARFILE,
                "bogus");

        checkVerifying(analyzer, 0, NOT_SIGNED_BY_ALIAS_VERIFYING_WARNING);

        // verify jar with non-existing alias in strict mode
        analyzer = jarsigner(
                "-verify",
                "-strict",
                "-keystore", KEYSTORE,
                "-storepass", PASSWORD,
                "-keypass", PASSWORD,
                SIGNED_JARFILE,
                "bogus");

        checkVerifying(analyzer, NOT_SIGNED_BY_ALIAS_EXIT_CODE,
                NOT_SIGNED_BY_ALIAS_VERIFYING_WARNING);

        System.out.println("Test passed");
    }

}
