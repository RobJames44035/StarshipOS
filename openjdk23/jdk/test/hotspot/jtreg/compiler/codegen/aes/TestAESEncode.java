/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package compiler.codegen.aes;

import javax.crypto.Cipher;

/**
 * @author Tom Deneau
 */
public class TestAESEncode extends TestAESBase {
    @Override
    public void run() {
        try {
            if (mode.equals("GCM")) {
                gcm_init(true);
            } else if (!noReinit) {
                cipher.init(Cipher.ENCRYPT_MODE, key, algParams);
            }
            encode = new byte[encodeLength];
            if (testingMisalignment) {
                int tempSize = cipher.update(input, encInputOffset, (msgSize - lastChunkSize), encode, encOutputOffset);
                cipher.doFinal(input, (encInputOffset + msgSize - lastChunkSize), lastChunkSize, encode, (encOutputOffset + tempSize));
            } else {
                cipher.doFinal(input, encInputOffset, msgSize, encode, encOutputOffset);
            }
            if (checkOutput) {
                compareArrays(encode, expectedEncode);
            }
        } catch (Exception e) {
            throw new Error(e.getMessage(), e);
        }
    }

    @Override
    void childShowCipher() {
        showCipher(cipher, "Encryption");
    }

}
