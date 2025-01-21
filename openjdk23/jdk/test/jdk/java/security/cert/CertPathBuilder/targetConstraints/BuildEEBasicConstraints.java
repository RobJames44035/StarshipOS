/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

// This test case relies on updated static security property, no way to re-use
// security property in samevm/agentvm mode.

/**
 * @test
 * @bug 6714842
 * @library /test/lib
 * @run main/othervm BuildEEBasicConstraints
 * @summary make sure a PKIX CertPathBuilder builds a path to an
 *      end entity certificate when the setBasicConstraints method of the
 *      X509CertSelector of the targetConstraints PKIXBuilderParameters
 *      parameter is set to -2.
 */

import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertPath;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.security.cert.X509CertSelector;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jdk.test.lib.security.CertUtils;

public final class BuildEEBasicConstraints {

    public static void main(String[] args) throws Exception {
        // reset the security property to make sure that the algorithms
        // and keys used in this test are not disabled.
        Security.setProperty("jdk.certpath.disabledAlgorithms", "MD2");

        X509Certificate rootCert = CertUtils.getCertFromFile("anchor.cer");
        TrustAnchor anchor = new TrustAnchor
            (rootCert.getSubjectX500Principal(), rootCert.getPublicKey(), null);
        X509CertSelector sel = new X509CertSelector();
        sel.setBasicConstraints(-2);
        PKIXBuilderParameters params = new PKIXBuilderParameters
            (Collections.singleton(anchor), sel);
        params.setRevocationEnabled(false);

        // Certs expired on 7th Nov 2022
        params.setDate(DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.US).parse("June 01, 2022"));

        X509Certificate eeCert = CertUtils.getCertFromFile("ee.cer");
        X509Certificate caCert = CertUtils.getCertFromFile("ca.cer");
        ArrayList<X509Certificate> certs = new ArrayList<X509Certificate>();
        certs.add(caCert);
        certs.add(eeCert);
        CollectionCertStoreParameters ccsp =
            new CollectionCertStoreParameters(certs);
        CertStore cs = CertStore.getInstance("Collection", ccsp);
        params.addCertStore(cs);
        PKIXCertPathBuilderResult res = CertUtils.build(params);
        CertPath cp = res.getCertPath();
        // check that first certificate is an EE cert
        List<? extends Certificate> certList = cp.getCertificates();
        X509Certificate cert = (X509Certificate) certList.get(0);
        if (cert.getBasicConstraints() != -1) {
            throw new Exception("Target certificate is not an EE certificate");
        }
    }
}
