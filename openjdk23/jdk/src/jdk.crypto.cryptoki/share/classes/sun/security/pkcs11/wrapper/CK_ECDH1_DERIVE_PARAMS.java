/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.pkcs11.wrapper;



/**
 * class CK_ECDH1_DERIVE_PARAMS provides the parameters to the
 * CKM_ECDH1_DERIVE and CKM_ECDH1_COFACTOR_DERIVE mechanisms.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_ECDH1_DERIVE_PARAMS {
 *   CK_EC_KDF_TYPE kdf;
 *   CK_ULONG ulSharedDataLen;
 *   CK_BYTE_PTR pSharedData;
 *   CK_ULONG ulPublicDataLen;
 *   CK_BYTE_PTR pPublicData;
 * } CK_ECDH1_DERIVE_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 */
public class CK_ECDH1_DERIVE_PARAMS {

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_EC_KDF_TYPE kdf;
     * </PRE>
     */
    public long kdf;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_ULONG ulSharedDataLen;
     *   CK_BYTE_PTR pSharedData;
     * </PRE>
     */
    public byte[] pSharedData;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_ULONG ulPublicDataLen;
     *   CK_BYTE_PTR pPublicData;
     * </PRE>
     */
    public byte[] pPublicData;

    public CK_ECDH1_DERIVE_PARAMS(long kdf, byte[] pSharedData, byte[] pPublicData) {
        this.kdf = kdf;
        this.pSharedData = pSharedData;
        this.pPublicData = pPublicData;
    }

    /**
     * Returns the string representation of CK_ECDH1_DERIVE_PARAMS.
     *
     * @return the string representation of CK_ECDH1_DERIVE_PARAMS
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Constants.INDENT);
        sb.append("kdf: 0x");
        sb.append(Functions.toFullHexString(kdf));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pSharedDataLen: ");
        sb.append(pSharedData.length);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pSharedData: ");
        sb.append(Functions.toHexString(pSharedData));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pPublicDataLen: ");
        sb.append(pPublicData.length);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pPublicData: ");
        sb.append(Functions.toHexString(pPublicData));
        //buffer.append(Constants.NEWLINE);

        return sb.toString();
    }

}
