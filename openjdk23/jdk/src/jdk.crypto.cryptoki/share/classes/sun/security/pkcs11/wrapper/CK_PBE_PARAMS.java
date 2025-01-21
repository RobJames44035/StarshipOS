/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * class CK_PBE_PARAMS provides all the necessary information required by
 * the CKM_PBE mechanisms and the CKM_PBA_SHA1_WITH_SHA1_HMAC mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_PBE_PARAMS {
 *   CK_BYTE_PTR pInitVector;
 *   CK_UTF8CHAR_PTR pPassword;
 *   CK_ULONG ulPasswordLen;
 *   CK_BYTE_PTR pSalt;
 *   CK_ULONG ulSaltLen;
 *   CK_ULONG ulIteration;
 * } CK_PBE_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public class CK_PBE_PARAMS {

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_BYTE_PTR pInitVector;
     * </PRE>
     */
    public byte[] pInitVector;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_UTF8CHAR_PTR pPassword;
     *   CK_ULONG ulPasswordLen;
     * </PRE>
     */
    public char[] pPassword;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_BYTE_PTR pSalt
     *   CK_ULONG ulSaltLen;
     * </PRE>
     */
    public byte[] pSalt;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_ULONG ulIteration;
     * </PRE>
     */
    public long ulIteration;

    public CK_PBE_PARAMS(char[] pPassword, byte[] pSalt, long ulIteration) {
         this.pPassword = pPassword;
         this.pSalt = pSalt;
         this.ulIteration = ulIteration;
     }

    /**
     * Returns the string representation of CK_PBE_PARAMS.
     *
     * @return the string representation of CK_PBE_PARAMS
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Constants.INDENT);
        sb.append("pInitVector: ");
        sb.append(pInitVector);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("ulPasswordLen: ");
        sb.append(pPassword.length);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("ulSaltLen: ");
        sb.append(pSalt.length);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pSalt: ");
        sb.append(pSalt);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("ulIteration: ");
        sb.append(ulIteration);
        //buffer.append(Constants.NEWLINE);

        return sb.toString();
    }

}
