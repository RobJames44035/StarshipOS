/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/* @test
 * @bug 8004502
 * @summary Sanity check that NoSuchAlgorithmException is thrown when requesting
 *   a CertStore of type "LDAP" and LDAP is not available.
 */

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertStore;
import java.security.cert.LDAPCertStoreParameters;


public class NoLDAP {
    public static void main(String[] args) throws Exception {
        try {
            Class.forName("javax.naming.ldap.LdapName");
            System.out.println("LDAP is present, test skipped");
            return;
        } catch (ClassNotFoundException ignore) { }

        try {
            CertStore.getInstance("LDAP", new LDAPCertStoreParameters());
            throw new RuntimeException("NoSuchAlgorithmException expected");
        } catch (NoSuchAlgorithmException x) {
            System.out.println("NoSuchAlgorithmException thrown as expected");
        }
    }
}
