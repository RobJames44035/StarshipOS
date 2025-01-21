/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6391482
 * @summary incorrect ASN1 DER encoding of DomainComponent AttributeValue
 * @modules java.base/sun.security.util
 *          java.base/sun.security.x509
 */

import javax.security.auth.x500.X500Principal;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.X500Name;

public class DomainComponentEncoding {

    public static void main(String[] args) throws Exception {
        // RFC 2253 String DN
        testDN("cn=hello, dc=com, dc=example");
        // RFC 1779 String DN with embedded quotes
        testDN("cn=hello, dc=\"com\", dc=example");
    }

    private static void testDN(String dn) throws Exception {
        X500Principal p = new X500Principal(dn);
        byte[] encoded = p.getEncoded();

        // name is a sequence of RDN's
        DerInputStream dis = new DerInputStream(encoded);
        DerValue[] nameseq = dis.getSequence(3);

        boolean passed = false;
        for (int i = 0; i < nameseq.length; i++) {

            // each RDN is a set of AttributeTypeAndValue
            DerInputStream is = new DerInputStream(nameseq[i].toByteArray());
            DerValue[] ava = is.getSet(3);

            for (int j = 0; j < ava.length; j++) {

                ObjectIdentifier oid = ava[j].data.getOID();

                if (oid.equals(X500Name.DOMAIN_COMPONENT_OID)) {
                    DerValue value = ava[j].data.getDerValue();
                    if (value.getTag() == DerValue.tag_IA5String) {
                        passed = true;
                        break;
                    } else {
                        throw new SecurityException
                                ("Test failed, expected DOMAIN_COMPONENT tag '" +
                                DerValue.tag_IA5String +
                                "', got '" +
                                value.getTag() + "'");
                    }
                }
            }

            if (passed) {
                break;
            }
        }

        if (passed) {
            System.out.println("Test passed");
        } else {
            throw new SecurityException("Test failed");
        }
    }
}
