/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.util.Arrays;
import java.util.List;
import jdk.test.lib.security.SecurityUtils;

/*
 * @test
 * @bug 8075286
 * @library /test/lib
 * @summary Test the SHAwithDSA signature algorithm OIDs in JDK.
 *          OID and algorithm transformation string should match.
 *          Both could be able to be used to generate the algorithm instance.
 * @compile ../../TestSignatureOidHelper.java
 * @run main TestSHAwithDSASignatureOids
 */
public class TestSHAwithDSASignatureOids {

    private static final List<OidAlgorithmPair> DATA = Arrays.asList(
            new OidAlgorithmPair("2.16.840.1.101.3.4.3.1", "SHA224withDSA"),
            new OidAlgorithmPair("2.16.840.1.101.3.4.3.2", "SHA256withDSA"));

    public static void main(String[] args) throws Exception {
        String kpgAlgorithm = "DSA";
        TestSignatureOidHelper helper = new TestSignatureOidHelper(kpgAlgorithm,
                System.getProperty("test.provider.name", "SUN"),
                SecurityUtils.getTestKeySize(kpgAlgorithm), DATA);
        helper.execute();
    }
}
