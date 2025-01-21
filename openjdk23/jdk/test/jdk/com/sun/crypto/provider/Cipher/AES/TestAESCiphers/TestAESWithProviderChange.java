/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.security.Security;

/*
 * @test
 * @bug 8044193
 * @summary Test AES ciphers with different modes and padding schemes after
 *  remove then add provider.
 * @modules java.base/com.sun.crypto.provider
 */

public class TestAESWithProviderChange extends Dynamic {
    public static void main(String argv[]) throws Exception {
        Security.removeProvider(PROVIDER);
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        new TestAESWithProviderChange().run(argv);
    }
}
