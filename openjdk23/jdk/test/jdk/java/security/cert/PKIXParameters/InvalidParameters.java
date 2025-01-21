/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4422738
 * @compile InvalidParameters.java
 * @run main InvalidParameters
 * @summary Make sure PKIXParameters(Set) and setTrustAnchors() detects invalid
 *          parameters and throws correct exceptions
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.Collections;
import java.util.Set;

public class InvalidParameters {

    public static void main(String[] args) throws Exception {

        TrustAnchor anchor = new TrustAnchor("cn=sean", new TestPublicKey(), null);
        PKIXParameters params = new PKIXParameters(Collections.singleton(anchor));

        // make sure empty Set of anchors throws InvAlgParamExc
        try {
            PKIXParameters p = new PKIXParameters(Collections.EMPTY_SET);
            throw new Exception("should have thrown InvalidAlgorithmParameterExc");
        } catch (InvalidAlgorithmParameterException iape) { }
        try {
            params.setTrustAnchors(Collections.EMPTY_SET);
            throw new Exception("should have thrown InvalidAlgorithmParameterExc");
        } catch (InvalidAlgorithmParameterException iape) { }

        // make sure null Set of anchors throws NullPointerException
        try {
            PKIXParameters p = new PKIXParameters((Set) null);
            throw new Exception("should have thrown NullPointerException");
        } catch (NullPointerException npe) { }
        try {
            params.setTrustAnchors((Set) null);
            throw new Exception("should have thrown NullPointerException");
        } catch (NullPointerException npe) { }

        // make sure Set of invalid objects throws ClassCastException
        @SuppressWarnings("unchecked") // Knowingly do something bad
        Set<TrustAnchor> badSet = (Set<TrustAnchor>) (Set) Collections.singleton(new String());
        try {
            PKIXParameters p = new PKIXParameters(badSet);
            throw new Exception("should have thrown ClassCastException");
        } catch (ClassCastException cce) { }
        try {
            params.setTrustAnchors(badSet);
            throw new Exception("should have thrown ClassCastException");
        } catch (ClassCastException cce) { }
    }

    static class TestPublicKey implements PublicKey {
        public String getAlgorithm() { return "Test"; }
        public String getFormat() { return null; }
        public byte[] getEncoded() { return null; }
    }
}
