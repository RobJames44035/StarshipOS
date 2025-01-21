/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.security.Provider;
import java.io.PrintStream;

/**
 * An utility class to create PBEWrapper object for the TestCipherSameBuffer
 * test.
 *
 * @author Alexander Fomin
 */
public class PBEWrapperCreator {

    private static final String PBKDF2 = "PBKDF2";
    private static final String AES = "AES";

    /**
     * Create PBEWrapper for the TestCipherSameBuffer test using given
     * parameters.
     *
     * @param p security provider
     * @param algo algorithms to test
     * @param passwd a password phrase
     * @param out print stream object
     * @return PBEWrapper in accordance to requested algorithm
     * @throws Exception all exception are thrown.
     */
    public static PBEWrapper createWrapper(Provider p, String algo,
            String passwd, PrintStream out) throws Exception {
        if (algo.toUpperCase().contains(PBKDF2)) {
            return new PBKDF2Wrapper(p, algo, passwd, out);
        } else if (algo.toUpperCase().contains(AES)) {
            return new AESPBEWrapper(p, algo, passwd, out);
        } else {
            return new PBECipherWrapper(p, algo, passwd, out);
        }
    }
}
