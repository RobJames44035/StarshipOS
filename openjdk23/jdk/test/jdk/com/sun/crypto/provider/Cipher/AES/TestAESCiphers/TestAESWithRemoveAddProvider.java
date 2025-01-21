/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.security.*;
import java.security.Provider;

/*
 * @test
 * @bug 8044193
 * @summary Test AES ciphers with different modes and padding schemes after
 *  remove default provider then add it back.
 */

public class TestAESWithRemoveAddProvider extends Dynamic {
    public static void main(String argv[]) throws Exception {
        Provider pJCE = Security.getProvider(PROVIDER);
        Security.removeProvider(PROVIDER);
        Security.addProvider(pJCE);
        new TestAESWithRemoveAddProvider().run(argv);
    }
}
