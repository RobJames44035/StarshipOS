/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package compiler.codegen.aes;

import javax.crypto.Cipher;

/**
 * @author Tom Deneau
 */
public class TestAESDecode extends TestAESBase {
    @Override
    public void run() {
        try {
            if (mode.equals("GCM")) {
                gcm_init(false);
            } else if (!noReinit) {
                dCipher.init(Cipher.DECRYPT_MODE, key, algParams);
            }
            decode = new byte[decodeLength];
            if (testingMisalignment) {
                int tempSize = dCipher.update(encode, encOutputOffset, (decodeMsgSize - lastChunkSize), decode, decOutputOffset);
                dCipher.doFinal(encode, (encOutputOffset + decodeMsgSize - lastChunkSize), lastChunkSize, decode, (decOutputOffset + tempSize));
            } else {
                dCipher.doFinal(encode, encOutputOffset, decodeMsgSize, decode, decOutputOffset);
            }
            if (checkOutput) {
                compareArrays(decode, expectedDecode);
            }
        } catch (Exception e) {
            throw new Error(e.getMessage(), e);
        }
    }

    @Override
    void childShowCipher() {
        showCipher(dCipher, "Decryption");
    }
}
