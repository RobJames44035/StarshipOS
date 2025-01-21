/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4416946
 * @summary Make sure {CertStore,CertPathBuilder,CertPathValidator,
 * CertificateFactory}.getInstance throws InvalidAlgorithmParameterException
 * if invalid params are specified and NoSuchAlgorithmException (or
 * CertificateException for CertificateFactory) if bogus type is specified
 */
import java.security.*;
import java.security.cert.*;
import java.util.*;

public class GetInstance {

    public static void main(String[] args) throws Exception {

        CollectionCertStoreParameters ccsp
            = new CollectionCertStoreParameters(new ArrayList());
        try {
            CertStore cs = CertStore.getInstance("LDAP", ccsp);
            throw new Exception("CertStore.getInstance() should have thrown " +
                "InvalidAlgorithmParameterException");
        } catch (InvalidAlgorithmParameterException iape) { }

        try {
            CertStore cs = CertStore.getInstance("BOGUS", null);
            throw new Exception("CertStore.getInstance() should have thrown " +
                "NoSuchAlgorithmException");
        } catch (NoSuchAlgorithmException nsae) { }

        try {
            CertPathBuilder cpb = CertPathBuilder.getInstance("BOGUS");
            throw new Exception("CertPathBuilder.getInstance() should have " +
                "thrown NoSuchAlgorithmException");
        } catch (NoSuchAlgorithmException nsae) { }

        try {
            CertPathValidator cpv = CertPathValidator.getInstance("BOGUS");
            throw new Exception("CertPathValidator.getInstance() should have " +
                "thrown NoSuchAlgorithmException");
        } catch (NoSuchAlgorithmException nsae) { }

        try {
            CertificateFactory cf = CertificateFactory.getInstance("BOGUS");
            throw new Exception("CertificateFactory.getInstance() should " +
                "have thrown CertificateException");
        } catch (CertificateException ce) { }
    }
}
