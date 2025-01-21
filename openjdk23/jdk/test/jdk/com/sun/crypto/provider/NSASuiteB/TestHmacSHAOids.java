/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/*
 * @test
 * @bug 8075286 8288050
 * @summary Test the HmacSHA algorithm OIDs in JDK.
 *          OID and Algorithm transformation string should match.
 *          Both could be able to be used to generate the algorithm instance.
 * @run main TestHmacSHAOids
 */
public class TestHmacSHAOids {

    private static final String PROVIDER_NAME =
            System.getProperty("test.provider.name", "SunJCE");
    private static final byte[] INPUT = "1234567890".getBytes();

    private static final List<DataTuple> DATA = Arrays.asList(
            new DataTuple("1.2.840.113549.2.7", "HmacSHA1"),
            new DataTuple("1.2.840.113549.2.8", "HmacSHA224"),
            new DataTuple("1.2.840.113549.2.9", "HmacSHA256"),
            new DataTuple("1.2.840.113549.2.10", "HmacSHA384"),
            new DataTuple("1.2.840.113549.2.11", "HmacSHA512"),
            new DataTuple("1.2.840.113549.2.12", "HmacSHA512/224"),
            new DataTuple("1.2.840.113549.2.13", "HmacSHA512/256")
    );

    public static void main(String[] args) throws Exception {
        for (DataTuple dataTuple : DATA) {
            runTest(dataTuple);
            System.out.println("passed");
        }
        System.out.println("All tests passed");
    }

    private static void runTest(DataTuple dataTuple)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidKeyException {
        Mac mcAlgorithm = Mac.getInstance(dataTuple.algorithm,
                PROVIDER_NAME);
        Mac mcOid = Mac.getInstance(dataTuple.oid, PROVIDER_NAME);

        if (mcAlgorithm == null) {
            throw new RuntimeException(String.format(
                    "Test failed: Mac using algorithm "
                            + "string %s getInstance failed.%n",
                    dataTuple.algorithm));
        }

        if (mcOid == null) {
            throw new RuntimeException(String.format(
                    "Test failed: Mac using OID %s getInstance failed.%n",
                    dataTuple.oid));
        }

        if (!mcAlgorithm.getAlgorithm().equals(dataTuple.algorithm)) {
            throw new RuntimeException(String.format(
                    "Test failed: Mac using algorithm string %s getInstance "
                            + "doesn't generate expected algorithm.%n",
                    dataTuple.algorithm));
        }

        KeyGenerator kg = KeyGenerator.getInstance(dataTuple.algorithm,
                PROVIDER_NAME);
        SecretKey key = kg.generateKey();

        mcAlgorithm.init(key);
        mcAlgorithm.update(INPUT);

        mcOid.init(key);
        mcOid.update(INPUT);

        // Comparison
        if (!Arrays.equals(mcAlgorithm.doFinal(), mcOid.doFinal())) {
            throw new RuntimeException("Digest comparison failed: "
                    + "the two MACs are not the same");
        }
    }

    private static class DataTuple {

        private final String oid;
        private final String algorithm;

        private DataTuple(String oid, String algorithm) {
            this.oid = oid;
            this.algorithm = algorithm;
        }
    }
}
