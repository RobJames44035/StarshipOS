/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8314045
 * @summary ArithmeticException in GaloisCounterMode
 */

import java.nio.ByteBuffer;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GCMShortInput {

    public static void main(String args[]) throws Exception {
        SecretKeySpec keySpec =
                new SecretKeySpec(
                        new byte[] {
                            88, 26, 43, -100, -24, -29, -70, 10, 34, -85, 52, 101, 45, -68, -105,
                            -123
                        },
                        "AES");
        GCMParameterSpec params =
                new GCMParameterSpec(8 * 16, new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, params);
        try {
            cipher.doFinal(ByteBuffer.allocate(0), ByteBuffer.allocate(0));
            throw new AssertionError("AEADBadTagException expected");
        } catch (AEADBadTagException e) {
            // expected
        }
    }
}
