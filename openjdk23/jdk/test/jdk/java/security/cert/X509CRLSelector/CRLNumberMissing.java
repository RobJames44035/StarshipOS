/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import jdk.test.lib.Asserts;
import sun.security.x509.CRLExtensions;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;

import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.cert.X509CRLSelector;
import java.util.Date;

/**
 * @test
 * @bug 8296399
 * @summary crlNumExtVal might be null inside X509CRLSelector::match
 * @library /test/lib
 * @modules java.base/sun.security.x509
 */

public class CRLNumberMissing {

    public static void main(String[] args) throws Exception {

        var pk = KeyPairGenerator.getInstance("Ed25519")
                .generateKeyPair().getPrivate();

        var crlWithoutNum = X509CRLImpl.newSigned(
                new X509CRLImpl.TBSCertList(
                        new X500Name("CN=CRL"), new Date(), new Date()),
                pk, "Ed25519");

        var exts = new CRLExtensions();
        exts.setExtension("CRLNumber", new CRLNumberExtension(1));
        var crlWithNum = X509CRLImpl.newSigned(
                new X509CRLImpl.TBSCertList(
                        new X500Name("CN=CRL"), new Date(), new Date(),
                        null, exts),
                pk, "Ed25519");

        var sel = new X509CRLSelector();
        Asserts.assertTrue(sel.match(crlWithNum));
        Asserts.assertTrue(sel.match(crlWithoutNum));

        sel = new X509CRLSelector();
        sel.setMinCRLNumber(BigInteger.ZERO);
        Asserts.assertTrue(sel.match(crlWithNum));
        Asserts.assertFalse(sel.match(crlWithoutNum));

        sel = new X509CRLSelector();
        sel.setMinCRLNumber(BigInteger.TWO);
        Asserts.assertFalse(sel.match(crlWithNum));
        Asserts.assertFalse(sel.match(crlWithoutNum));
    }
}
