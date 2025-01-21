/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * class CK_RSA_PKCS_OAEP_PARAMS provides the parameters to the
 * CKM_RSA_PKCS_OAEP mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_RSA_PKCS_OAEP_PARAMS {
 *   CK_MECHANISM_TYPE hashAlg;
 *   CK_RSA_PKCS_OAEP_MGF_TYPE mgf;
 *   CK_RSA_PKCS_OAEP_SOURCE_TYPE source;
 *   CK_VOID_PTR pSourceData;
 *   CK_ULONG ulSourceDataLen;
 * } CK_RSA_PKCS_OAEP_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public class CK_RSA_PKCS_OAEP_PARAMS {

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_MECHANISM_TYPE hashAlg;
     * </PRE>
     */
    public long hashAlg;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_RSA_PKCS_OAEP_MGF_TYPE mgf;
     * </PRE>
     */
    public long mgf;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_RSA_PKCS_OAEP_SOURCE_TYPE source;
     * </PRE>
     */
    public long source;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_VOID_PTR pSourceData;
     *   CK_ULONG ulSourceDataLen;
     * </PRE>
     */
    public byte[] pSourceData;

    //CK_ULONG ulSourceDataLen;
    // ulSourceDataLen == pSourceData.length

    /**
     * Returns the string representation of CK_RSA_PKCS_OAEP_PARAMS.
     *
     * @return the string representation of CK_RSA_PKCS_OAEP_PARAMS
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Constants.INDENT);
        sb.append("hashAlg: ");
        sb.append(hashAlg);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("mgf: ");
        sb.append(mgf);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("source: ");
        sb.append(source);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pSourceData: ");
        sb.append(pSourceData.toString());
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pSourceDataLen: ");
        sb.append(Functions.toHexString(pSourceData));
        //buffer.append(Constants.NEWLINE);

        return sb.toString() ;
    }

}
