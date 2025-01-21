/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4162868 8130181 8242151 8267397
 * @modules java.base/sun.security.x509
 * @modules java.base/sun.security.util
 * @run main/othervm ExtensibleAlgorithmId
 * @summary Check that AlgorithmId Name-to-OID mapping is extensible and
 *      up-to-date.
 */

import java.security.*;
import sun.security.x509.AlgorithmId;

public class ExtensibleAlgorithmId {

    private static void test(String alg, String expOid) throws Exception {
        System.out.println("Testing " + alg + " and " + expOid );
        try {
            AlgorithmId algid = AlgorithmId.get(alg);
            if (expOid == null) {
                throw new Exception("Expected NSAE not thrown");
            }
            if (!expOid.equals(algid.getOID().toString())) {
                throw new Exception("Oid mismatch, expected " + expOid +
                        ", got " + algid.getOID().toString());
            }
            if (!alg.equals(algid.getName())) {
                throw new Exception("Name mismatch, expected " + alg +
                        ", got " + algid.getName());
            }
            // try AlgorithmId.get() using 'expOid' if (alg != expOid)
            if (alg != expOid) {
                algid = AlgorithmId.get(expOid);
                if (!expOid.equals(algid.getOID().toString())) {
                    throw new Exception("Oid2 mismatch, expected " + expOid +
                        ", got " + algid.getOID().toString());
                }
                if (!alg.equals(algid.getName())) {
                    throw new Exception("Name2 mismatch, expected " + alg +
                            ", got " + algid.getName());
                }
            }
            System.out.println(" => passed");
        } catch (NoSuchAlgorithmException nsae) {
            if (expOid != null) {
                nsae.printStackTrace();
                throw new Exception("Unexpected NSAE for " + alg);
            }
            System.out.println(" => expected NSAE thrown");
        }
    }

    public static void main(String[] args) throws Exception {

        TestProvider p = new TestProvider();
        String alias = "Alg.Alias.Signature.OID." + TestProvider.ALG_OID;
        String stdAlgName = p.getProperty(alias);
        if (stdAlgName == null ||
                !stdAlgName.equalsIgnoreCase(TestProvider.ALG_NAME)) {
            throw new Exception("Wrong OID");
        }

        // scenario#1: test before adding TestProvider
        System.out.println("Before adding test provider");
        test(TestProvider.ALG_NAME, null);
        test(TestProvider.ALG_OID, TestProvider.ALG_OID);
        test(TestProvider.ALG_OID2, TestProvider.ALG_OID2);

        Security.addProvider(p);
        // scenario#2: test again after adding TestProvider
        System.out.println("After adding test provider");
        test(TestProvider.ALG_NAME, TestProvider.ALG_OID);
        test(TestProvider.ALG_OID2, TestProvider.ALG_OID2);

        Security.removeProvider(p.getName());
        // scenario#3: test after removing TestProvider; should be same as
        // scenario#1
        System.out.println("After removing test provider");
        test(TestProvider.ALG_NAME, null);
        test(TestProvider.ALG_OID, TestProvider.ALG_OID);
        test(TestProvider.ALG_OID2, TestProvider.ALG_OID2);
    }

    static class TestProvider extends Provider {

        static String ALG_OID = "1.2.3.4.5.6.7.8.9.0";
        static String ALG_OID2 = "0.2.7.6.5.4.3.2.1.0";
        static String ALG_NAME = "XYZ";

        public TestProvider() {
            super("Dummy", "1.0", "XYZ algorithm");

            put("Signature." + ALG_NAME, "test.xyz");
            // preferred OID for name<->oid mapping
            put("Alg.Alias.Signature.OID." + ALG_OID, ALG_NAME);
            put("Alg.Alias.Signature." + ALG_OID2, ALG_NAME);
        }
    }
}
