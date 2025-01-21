/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Change default criticality of policy mappings and policy constraints
            certificate extensions
 * @bug 8059916
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 */

import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PolicyConstraintsExtension;
import sun.security.x509.PolicyMappingsExtension;

import java.util.List;

public class DefaultCriticality {
    public static void main(String [] args) throws Exception {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 1);
        if (!pce.isCritical()) {
            throw new Exception("PolicyConstraintsExtension should be " +
                                "critical by default");
        }

        CertificatePolicyId id = new CertificatePolicyId(ObjectIdentifier.of("1.2.3.4"));
        PolicyMappingsExtension pme = new PolicyMappingsExtension(List.of(
                new CertificatePolicyMap(id, id)));
        if (!pme.isCritical()) {
            throw new Exception("PolicyMappingsExtension should be " +
                                "critical by default");
        }

        System.out.println("Test passed.");
    }
}
