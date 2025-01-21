/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8279801
 * @summary EC KeyFactory and KeyPairGenerator do not have aliases for OID format
 * @modules java.base/sun.security.util
 *          jdk.crypto.ec
 */

import sun.security.util.KnownOIDs;

import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;

public class OidInstance {
    public static void main(String[] args) throws Exception {
        String oid = KnownOIDs.EC.value();
        KeyFactory.getInstance(oid, System.getProperty("test.provider.name", "SunEC"));
        KeyPairGenerator.getInstance(oid, System.getProperty("test.provider.name", "SunEC"));
        AlgorithmParameters.getInstance(oid, System.getProperty("test.provider.name", "SunEC"));
    }
}
