/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Wrapper class to test a given SecretKeyFactory.PBKDF2 algorithm.
 */
public class PBKDF2Wrapper extends AbstractPBEWrapper {
    /**
     * Default salt size.
     */
    public static final int PBKDF2_SALT_SIZE = 64;

    /**
     * Default key length.
     */
    public static final int PKDF2_DEFAULT_KEY_LEN = 128;

    /**
     * Default transformation.
     */
    public static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * Algorithm name.
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * Initialization vector length.
     */
    private static final int IV_LENGTH = 16;

    /**
     * The buffer with the IV.
     */
    private final byte[] iv;

    /**
     * PBKDF2Wrapper constructor. Instantiate Cipher using
     * "AES/CBC/PKCS5Padding" transformation. Generate a secret key using PKDF2
     * algorithms given in the "algo" parameter.
     *
     * @param algo AES-based PBE algorithm.
     * @param passwd password phrase.
     * @throws GeneralSecurityException all security exceptions are thrown.
     */
    public PBKDF2Wrapper(PBEAlgorithm algo, String passwd)
            throws GeneralSecurityException {
        super(algo, passwd, PBKDF2_SALT_SIZE);
        iv = TestUtilities.generateBytes(IV_LENGTH);
    }

    /**
     * Initiate the Cipher object for PBKDF2 algorithm using given "mode".
     *
     * @param mode Cipher mode: encrypt or decrypt
     * @return Cipher object for PBKDF2 algorithm
     * @throws GeneralSecurityException all security exceptions are thrown.
     */
    @Override
    protected Cipher initCipher(int mode) throws GeneralSecurityException {
        String providerName = System.getProperty("test.provider.name", "SunJCE");
        Provider provider = Security.getProvider(providerName);
        if (provider == null) {
            throw new RuntimeException(providerName + ": provider does not exist.");
        }
        // Generate secret key
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(),
                salt, DEFAULT_ITERATION, PKDF2_DEFAULT_KEY_LEN);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(baseAlgo);
        SecretKey key = keyFactory.generateSecret(pbeKeySpec);

        // get Cipher instance
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION, provider);
        cipher.init(mode,
                new SecretKeySpec(key.getEncoded(),KEY_ALGORITHM),
                new IvParameterSpec(iv));
        return cipher;
    }
}
