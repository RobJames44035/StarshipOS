/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

/**
 * This is an abstract class for CipherInputStream/CipherOutputStream PBE
 * functional tests.
 */
public abstract class CICO_PBE_Test {
    /**
     * Sample string for byte buffer.
     */
    public static final String BYTE_ARR_BUFFER = "byteArrayBuffering";

    /**
     * Sample string for int buffer.
     */
    public static final String INT_BYTE_BUFFER = "intByteBuffering";
    public static final String PASS_PHRASE = "Some password phrase!";

    /**
     * Text string size.
     */
    public static final int TEXT_SIZE = 800;

    protected final byte[] plainText;
    private final Cipher encryptCipher, decryptCipher;

    /**
     * An CipherInputStream for reading cipher and plain text.
     */
    private final CipherInputStream ciInput;

    /**
     * Constructor by algorithm.
     * @param pbeAlgo PBE algorithm to test.
     * @throws GeneralSecurityException if any security error.
     */
    public CICO_PBE_Test(PBEAlgorithm pbeAlgo) throws GeneralSecurityException {
        // Do initialization of the plainText
        plainText = TestUtilities.generateBytes(TEXT_SIZE);
        // Do initialization of the ciphers
        AbstractPBEWrapper pbeWrap = AbstractPBEWrapper.createWrapper(pbeAlgo, PASS_PHRASE);
        encryptCipher = pbeWrap.initCipher(Cipher.ENCRYPT_MODE);
        decryptCipher = pbeWrap.initCipher(Cipher.DECRYPT_MODE);
        // init cipher input stream
        ciInput = new CipherInputStream(new ByteArrayInputStream(plainText),
                encryptCipher);
    }

    protected byte[] getPlainText() {
        return plainText;
    }

    /**
     * The body of the test. Should be defined in subclasses.
     * @param type byteArrayBuffering or intByteBuffering
     * @throws IOException I/O operation failed.
     * @throws GeneralSecurityException all exceptions thrown.
     */
    protected abstract void proceedTest(String type)
            throws IOException, GeneralSecurityException;

    protected Cipher getEncryptCipher() {
        return encryptCipher;
    }

    public CipherInputStream getCiInput() {
        return ciInput;
    }

    public Cipher getDecryptCipher() {
        return decryptCipher;
    }
}
