/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @author Valerie PENG
 * @author Yun Ke
 * @author Alexander Fomin
 * @author rhalade
 */
import java.security.spec.AlgorithmParameterSpec;

import java.util.StringTokenizer;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import java.io.PrintStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PBECipherWrapper extends PBEWrapper {

    private final AlgorithmParameterSpec aps;

    public PBECipherWrapper(
            Provider p, String algo, String passwd, PrintStream out)
            throws Exception {
        super(algo,
                SecretKeyFactory.getInstance(
                        new StringTokenizer(algo, "/").nextToken(), p).generateSecret(
                        new PBEKeySpec(passwd.toCharArray())),
                Cipher.getInstance(algo, p), out);

        int SALT_SIZE = 8;
        aps = new PBEParameterSpec(generateSalt(SALT_SIZE), ITERATION_COUNT);
    }

    @Override
    public boolean execute(int edMode, byte[] inputText, int offset,
            int len) {
        StringTokenizer st = new StringTokenizer(algo, "/");
        String baseAlgo = st.nextToken().toUpperCase();

        boolean isUnlimited;
        try {
            isUnlimited =
                (Cipher.getMaxAllowedKeyLength(this.algo) == Integer.MAX_VALUE);
        } catch (NoSuchAlgorithmException nsae) {
            out.println("Got unexpected exception for " + this.algo);
            nsae.printStackTrace(out);
            return false;
        }

        // Perform encryption or decryption depends on the specified edMode
        try {
            ci.init(edMode, key, aps);
            if ((baseAlgo.endsWith("TRIPLEDES")
                    || baseAlgo.endsWith("AES_256")) && !isUnlimited) {
                out.print("Expected InvalidKeyException not thrown: "
                    + this.algo);
                return false;
            }

            // First, generate the cipherText at an allocated buffer
            byte[] outputText = ci.doFinal(inputText, offset, len);

            // Second, generate cipherText again at the same buffer of
            // plainText
            int myoff = offset / 2;
            int off = ci.update(inputText, offset, len, inputText, myoff);

            ci.doFinal(inputText, myoff + off);

            // Compare to see whether the two results are the same or not
            boolean result = equalsBlock(inputText, myoff, outputText, 0,
                    outputText.length);

            return result;
        } catch (Exception ex) {
            if ((ex instanceof InvalidKeyException) &&
                    (baseAlgo.endsWith("TRIPLEDES")
                        || baseAlgo.endsWith("AES_256")) &&
                !isUnlimited) {
                out.println("Expected InvalidKeyException thrown for "
                    + algo);
                return true;
            } else {
                out.println("Got unexpected exception for " + algo);
                ex.printStackTrace(out);
                return false;
            }
        }
    }
}
