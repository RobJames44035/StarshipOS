/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4991790
 * @summary Make sure NullCipher can be used without initialization.
 * @author Valerie Peng
 */
import java.io.PrintStream;
import java.util.Random;
import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public class TestWithoutInit {

    public static void main(String argv[]) throws Exception {
        // Initialization
        Cipher ci = new NullCipher();

        byte[] in = new byte[8];

        // try calling doFinal() directly
        ci.doFinal(in);

        // try calling update() directly
        ci.update(in);
        ci.doFinal(); // reset cipher state

        // try calling wrap() and unwrap() directly
        Key key = new SecretKeySpec(in, "any");
        try {
            ci.wrap(key);
        } catch (UnsupportedOperationException uoe) {
            // expected
        }
        try {
            ci.unwrap(in, "any", Cipher.SECRET_KEY);
        } catch (UnsupportedOperationException uoe) {
            // expected
        }
        System.out.println("Test Passed");
    }
}
