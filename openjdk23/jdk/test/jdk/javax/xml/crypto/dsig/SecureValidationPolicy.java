/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8151893 8259709
 * @summary Tests for the jdk.xml.dsig.secureValidationPolicy security property
 * @modules java.xml.crypto/org.jcp.xml.dsig.internal.dom
 */

import java.security.Security;
import java.util.List;
import org.jcp.xml.dsig.internal.dom.Policy;

public class SecureValidationPolicy {

    public static void main(String[] args) throws Exception {

        List<String> restrictedSchemes = List.of("file:/tmp/foo",
            "http://java.com", "https://java.com");
        List<String> restrictedAlgs = List.of(
            "http://www.w3.org/TR/1999/REC-xslt-19991116",
            "http://www.w3.org/2001/04/xmldsig-more#rsa-md5",
            "http://www.w3.org/2001/04/xmldsig-more#hmac-md5",
            "http://www.w3.org/2001/04/xmldsig-more#md5",
            "http://www.w3.org/2000/09/xmldsig#sha1",
            "http://www.w3.org/2000/09/xmldsig#dsa-sha1",
            "http://www.w3.org/2000/09/xmldsig#rsa-sha1",
            "http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1",
            "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1");

        // Test expected defaults
        System.out.println("Testing defaults");
        if (!Policy.restrictNumTransforms(6)) {
            throw new Exception("maxTransforms not enforced");
        }
        if (!Policy.restrictNumReferences(31)) {
            throw new Exception("maxReferences not enforced");
        }
        for (String scheme : restrictedSchemes) {
            if (!Policy.restrictReferenceUriScheme(scheme)) {
                throw new Exception(scheme + " scheme not restricted");
            }
        }
        for (String alg : restrictedAlgs) {
            if (!Policy.restrictAlg(alg)) {
                throw new Exception(alg + " alg not restricted");
            }
        }
        if (!Policy.restrictDuplicateIds()) {
            throw new Exception("noDuplicateIds not enforced");
        }
        if (!Policy.restrictRetrievalMethodLoops()) {
            throw new Exception("noRetrievalMethodLoops not enforced");
        }
    }
}
