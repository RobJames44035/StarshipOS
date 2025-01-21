/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8050374 8146293 8172366
 * @summary Verify a chain of signed objects
 * @library /test/lib
 * @build jdk.test.lib.SigTestUtil
 * @compile ../../../java/security/SignedObject/Chain.java
 * @run main SignedObjectChain
 */
public class SignedObjectChain {

    private static class Test extends Chain.Test {

        public Test(Chain.SigAlg sigAlg) {
            super(sigAlg, Chain.KeyAlg.EC, Chain.Provider.TestProvider_or_SunEC);
        }
    }

    private static final Test[] tests = {
        new Test(Chain.SigAlg.SHA1withECDSA),
        new Test(Chain.SigAlg.SHA224withECDSA),
        new Test(Chain.SigAlg.SHA256withECDSA),
        new Test(Chain.SigAlg.SHA384withECDSA),
        new Test(Chain.SigAlg.SHA512withECDSA),
        new Test(Chain.SigAlg.SHA3_224withECDSA),
        new Test(Chain.SigAlg.SHA3_256withECDSA),
        new Test(Chain.SigAlg.SHA3_384withECDSA),
        new Test(Chain.SigAlg.SHA3_512withECDSA),
    };

    public static void main(String argv[]) {
        boolean resutl = java.util.Arrays.stream(tests).allMatch(
                (test) -> Chain.runTest(test));

        if(resutl) {
            System.out.println("All tests passed");
        } else {
            throw new RuntimeException("Some tests failed");
        }
    }
}
