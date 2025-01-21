/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 8242141
 * @summary New System Properties to configure the default signature schemes
 * @library /javax/net/ssl/templates
 * @run main/othervm CustomizedClientSchemes
 */

import javax.net.ssl.SSLException;

public class CustomizedClientSchemes extends SSLSocketTemplate {

    public static void main(String[] args) throws Exception {
        System.setProperty("jdk.tls.client.SignatureSchemes", "rsa_pkcs1_sha1");

        try {
            new CustomizedClientSchemes().run();
            throw new Exception(
                "The jdk.tls.client.SignatureSchemes System Property " +
                "does not work");
        } catch (SSLException e) {
            // Got the expected exception.
        }
    }
}
