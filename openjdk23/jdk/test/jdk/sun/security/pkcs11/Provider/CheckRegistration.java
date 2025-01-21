/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
/*
 * @test
 * @bug 8258851
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 * @run main CheckRegistration
 * @summary Ensure SunPKCS11 provider service registration matches the actual
 *     impl class
 */

import java.security.Provider;
import java.util.Set;

public class CheckRegistration extends PKCS11Test {

    public static void main(String[] args) throws Exception {
        main(new CheckRegistration(), args);
    }

    @Override
    public void main(Provider p) throws Exception {
        Set<Provider.Service> services = p.getServices();

        for (Provider.Service s : services) {
            String key = s.getType() + "." + s.getAlgorithm();
            Object val = p.get(key);
            System.out.println("Checking " + key + " : " + s.getClassName());
            if (val == null) {
                throw new RuntimeException("Missing mapping");
            }
            if (!s.getClassName().equals(val)) {
                System.out.println("Mapping value: " + val);
                throw new RuntimeException("Mapping mismatches");
            }
            Object o = s.newInstance(null);
            if (!s.getClassName().equals(o.getClass().getName())) {
                System.out.println("Actual impl: " + o.getClass().getName());
                throw new RuntimeException("Impl class mismatches");
            }
        }
        System.out.println("Test Passed");
    }
}
