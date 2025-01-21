/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * class CK_ECDH2_DERIVE_PARAMS provides the parameters to the
 * CKM_ECMQV_DERIVE mechanism.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_ECDH2_DERIVE_PARAMS {
 *   CK_EC_KDF_TYPE kdf;
 *   CK_ULONG ulSharedDataLen;
 *   CK_BYTE_PTR pSharedData;
 *   CK_ULONG ulPublicDataLen;
 *   CK_BYTE_PTR pPublicData;
 *   CK_ULONG ulPrivateDataLen;
 *   CK_OBJECT_HANDLE hPrivateData;
 *   CK_ULONG ulPublicDataLen2;
 *   CK_BYTE_PTR pPublicData2;
 * } CK_ECDH2_DERIVE_PARAMS;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 */
public class CK_ECDH2_DERIVE_PARAMS {

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

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_ULONG ulPrivateDataLen;
     * </PRE>
     */
    public long ulPrivateDataLen;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_OBJECT_HANDLE hPrivateData;
     * </PRE>
     */
    public long hPrivateData;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_ULONG ulPublicDataLen2;
     *   CK_BYTE_PTR pPublicData2;
     * </PRE>
     */
    public byte[] pPublicData2;

    /**
     * Returns the string representation of CK_PKCS5_PBKD2_PARAMS.
     *
     * @return the string representation of CK_PKCS5_PBKD2_PARAMS
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
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("ulPrivateDataLen: ");
        sb.append(ulPrivateDataLen);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("hPrivateData: ");
        sb.append(hPrivateData);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pPublicDataLen2: ");
        sb.append(pPublicData2.length);
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("pPublicData2: ");
        sb.append(Functions.toHexString(pPublicData2));
        //buffer.append(Constants.NEWLINE);

        return sb.toString();
    }

}
