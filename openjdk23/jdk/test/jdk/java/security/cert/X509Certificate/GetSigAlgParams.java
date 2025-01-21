/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8259428
 * @library /test/lib
 * @summary Verify X509Certificate.getSigAlgParams() returns new array each
 *          time it is called
 * @modules java.base/sun.security.tools.keytool java.base/sun.security.x509
 */

import java.security.cert.X509Certificate;
import jdk.test.lib.security.SecurityUtils;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class GetSigAlgParams {

    public static void main(String[] args) throws Exception {

        CertAndKeyGen cakg = new CertAndKeyGen("RSASSA-PSS", "RSASSA-PSS");
        cakg.generate(SecurityUtils.getTestKeySize("RSA"));
        X509Certificate c = cakg.getSelfCertificate(new X500Name("CN=Me"), 100);
        if (c.getSigAlgParams() == c.getSigAlgParams()) {
            throw new Exception("Encoded params are the same byte array");
        }
    }
}
