/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;

import static sun.security.pkcs11.wrapper.PKCS11Constants.*;

/**
 * class CK_PKCS5_PBKD2_PARAMS provides the parameters to the CKM_PKCS5_PBKD2
 * mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_PKCS5_PBKD2_PARAMS {
 *   CK_PKCS5_PBKDF2_SALT_SOURCE_TYPE saltSource;
 *   CK_VOID_PTR pSaltSourceData;
 *   CK_ULONG ulSaltSourceDataLen;
 *   CK_ULONG iterations;
 *   CK_PKCS5_PBKD2_PSEUDO_RANDOM_FUNCTION_TYPE prf;
 *   CK_VOID_PTR pPrfData;
 *   CK_ULONG ulPrfDataLen;
 *   CK_UTF8CHAR_PTR pPassword;
 *   CK_ULONG_PTR ulPasswordLen;
 * } CK_PKCS5_PBKD2_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public class CK_PKCS5_PBKD2_PARAMS {

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_PKCS5_PBKDF2_SALT_SOURCE_TYPE saltSource;
     * </PRE>
     */
    public long saltSource;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_VOID_PTR pSaltSourceData;
     *   CK_ULONG ulSaltSourceDataLen;
     * </PRE>
     */
    public byte[] pSaltSourceData;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_ULONG iterations;
     * </PRE>
     */
    public long iterations;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_PKCS5_PBKD2_PSEUDO_RANDOM_FUNCTION_TYPE prf;
     * </PRE>
     */
    public long prf;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_VOID_PTR pPrfData;
     *   CK_ULONG ulPrfDataLen;
     * </PRE>
     */
    public byte[] pPrfData;

    /**
     * <b>PKCS#11:</b>
     * <pre>
     *   CK_UTF8CHAR_PTR pPassword
     *   CK_ULONG_PTR ulPasswordLen;
     * </pre>
     */
    public char[] pPassword;

    public CK_PKCS5_PBKD2_PARAMS(char[] pPassword, byte[] pSalt,
            long iterations, long prf) {
        this.pPassword = pPassword;
        this.pSaltSourceData = pSalt;
        this.iterations = iterations;
        this.prf = prf;
        this.saltSource = CKZ_SALT_SPECIFIED;
    }

    /**
     * Returns the string representation of CK_PKCS5_PBKD2_PARAMS.
     *
     * @return the string representation of CK_PKCS5_PBKD2_PARAMS
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Constants.INDENT);
        sb.append("saltSource: ");
        sb.append(Functions.getParamSourcesName(saltSource));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pSaltSourceData: ");
        sb.append(Functions.toHexString(pSaltSourceData));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("ulSaltSourceDataLen: ");
        sb.append(Functions.getLength(pSaltSourceData));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("iterations: ");
        sb.append(iterations);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("prf: ");
        sb.append(Functions.getPrfName(prf));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pPrfData: ");
        sb.append(Functions.toHexString(pPrfData));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("ulPrfDataLen: ");
        sb.append(Functions.getLength(pPrfData));
        //buffer.append(Constants.NEWLINE);

        return sb.toString();
    }

}
