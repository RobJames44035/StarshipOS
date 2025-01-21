/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8312306
 * @summary Check the destroy()/isDestroyed() of the PBEKey impl from SunJCE
 * @library /test/lib
 * @run testng/othervm PBEKeyDestroyTest
 */
import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.charset.StandardCharsets;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PBEKeyDestroyTest {

    @Test
    public void test() throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec("12345678".toCharArray(),
                "abcdefgh".getBytes(StandardCharsets.UTF_8), 100000, 128 >> 3);

        SecretKeyFactory skf = SecretKeyFactory.getInstance
                ("PBEWithHmacSHA1AndAES_128", "SunJCE");

        SecretKey key1 = skf.generateSecret(keySpec);
        SecretKey key2 = skf.generateSecret(keySpec);

        // should be equal
        Assert.assertFalse(key1.isDestroyed());
        Assert.assertFalse(key2.isDestroyed());
        Assert.assertTrue(key1.equals(key2));
        Assert.assertTrue(key2.equals(key1));

        // destroy key1
        key1.destroy();
        Assert.assertTrue(key1.isDestroyed());
        Assert.assertFalse(key1.equals(key2));
        Assert.assertFalse(key2.equals(key1));

        // also destroy key2
        key2.destroy();
        Assert.assertTrue(key2.isDestroyed());
        Assert.assertFalse(key1.equals(key2));
        Assert.assertFalse(key2.equals(key1));

        // call destroy again to make sure no unexpected exceptions
        key2.destroy();
    }
}
