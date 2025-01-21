/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8336665
 * @summary Verify that generateCRLs method does not throw ClassCastException.
 *          It should throw CRLException instead.
 * @library /test/lib
 */
import java.security.NoSuchProviderException;
import java.security.cert.*;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import jdk.test.lib.Utils;

public class UnexpectedCCE {
    static CertificateFactory cf = null;

    public static void main(String[] av ) throws CertificateException,
           NoSuchProviderException {

        // Fuzzed data input stream looks like an x509.OIDName
        // in the CertificateIssuerExtension. A CRLException is thrown
        // because an X500Name is expected.
        byte[] encoded_1 = Base64.getDecoder().decode("""
            MIIBljCCAVMCAQEwCwYHKoZIzjgEAwUAMC0xEzARBgoJkiaJk/IsZAEZEwNjb20xFjA\
            UBgoJkiaJjvIsZAEZEwZ0ZXN0Q0EXDTAzMDcxNTE2MjAwNVoXDTAzMDcyMDE2MjAwNV\
            owgdIwUwIBBBcNMDMwNzE1MTYyMDAzWjA/MD0GA1UdHQEB/wQzMDGILzETMBEGCgmSJ\
            omT8ixkARkMA2NvbTEYMBYGCgmSJomT8ixkARkTCGNlcnRzUlVTMBICAQMXDTAzMDcx\
            NTE2MjAwNFowUwIBAhcNMDMwNzE1MTYyMDA0WjA/MD0GA1UdIQEB/wQzMDEwGAYDVQQ\
            DExEwDyqGMDEUMgAwgDAuRQA1MRYGCgmSJomT8ixkARkTCG15VGVzdENBMBICAQEXDT\
            AzMDcxNTE2MjAwNFqgHzAdMA8GA1UdHAEB/wQFMAOEAf8wCgYDVR0UAwACAQIwCwYHK\
            oZIzjgEAwUAAzAAMC0CFBaZDryEEOr8Cw7sOAAAAKaDgtHcAhUAkUenJpwYZgS6IPjy\
            AjZG+RfHdO4=""");

        // Fuzzed data input stream looks like an x509.X400Address
        // in the CertificateIssuerExtension. A CRLException is thrown
        // because an X500Name is expected.
        byte[] encoded_2 = Base64.getDecoder().decode("""
            MIIBljCCAVMCAQEwCwYHKoZIzjgEAwUAMC0xEzARBgoJkiaJk/IsZAEZEwNjb20xFjA\
            UBgoJkiaJk/IsZAEZEwZ0ZXN0J0EXDTAzMDcxNTE2MjAwNVoXDTAzMDcyMDE2MjAwNV\
            owgdIwUwIBBBcNMDMwNzE1MTYyMDA0WjA/MD0GA1UdHQEB/wQzMDGkLzETMBEGCgmSJ\
            omT8ixkARkTA2NvbTEYMBYGCgmSJomT8ixkARkTCGNlcnRzUlVTMBICAQMXDTAzMDcx\
            NTE2MjAwNFowUwIBAhcNMDMwNzE1MTYyMDA0WjA/MD0GA1UdHQEB/wQzMDGjLzETMBE\
            GCgmSJomT8ixkARkTA2NvGG0wMRYGCgmSJomT8ixkARkTCG15VGVzdENBMBICAQEXDT\
            AzMDcxNTE2MjAwNVqgHzAdMGAGA1UdHAEB/wQFMAOEAf8wCgYDVR0UBAMCAQIwCwYHK\
            oZIzjgEAwUAAzAAMC0CFBaZDryEEOr8Cw7sJa07gqaDgtHcAhUAkUenJpwYZgS6IPjy\
            AjZG+RfHdO4=""");

        cf = CertificateFactory.getInstance("X.509", "SUN");

        run(encoded_1);
        run(encoded_2);
    }

    private static void run(byte[] buf) {
        Utils.runAndCheckException(
                () -> cf.generateCRLs(new ByteArrayInputStream(buf)),
                CRLException.class);
    }
}
