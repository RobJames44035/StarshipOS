/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.pkcs11.wrapper;



/**
 * class CK_SSL3_RANDOM_DATA provides information about the random data of a
 * client and a server in an SSL context. This class is used by both the
 * CKM_SSL3_MASTER_KEY_DERIVE and the CKM_SSL3_KEY_AND_MAC_DERIVE mechanisms.
 * <p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_SSL3_RANDOM_DATA {
 *   CK_BYTE_PTR pClientRandom;
 *   CK_ULONG ulClientRandomLen;
 *   CK_BYTE_PTR pServerRandom;
 *   CK_ULONG ulServerRandomLen;
 * } CK_SSL3_RANDOM_DATA;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public class CK_SSL3_RANDOM_DATA {

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_BYTE_PTR pClientRandom;
     *   CK_ULONG ulClientRandomLen;
     * </PRE>
     */
    public byte[] pClientRandom;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_BYTE_PTR pServerRandom;
     *   CK_ULONG ulServerRandomLen;
     * </PRE>
     */
    public byte[] pServerRandom;

    public CK_SSL3_RANDOM_DATA(byte[] clientRandom, byte[] serverRandom) {
        pClientRandom = clientRandom;
        pServerRandom = serverRandom;
    }

    /**
     * Returns the string representation of CK_SSL3_RANDOM_DATA.
     *
     * @return the string representation of CK_SSL3_RANDOM_DATA
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(Constants.INDENT);
        buffer.append("pClientRandom: ");
        buffer.append(Functions.toHexString(pClientRandom));
        buffer.append(Constants.NEWLINE);

        buffer.append(Constants.INDENT);
        buffer.append("ulClientRandomLen: ");
        buffer.append(pClientRandom.length);
        buffer.append(Constants.NEWLINE);

        buffer.append(Constants.INDENT);
        buffer.append("pServerRandom: ");
        buffer.append(Functions.toHexString(pServerRandom));
        buffer.append(Constants.NEWLINE);

        buffer.append(Constants.INDENT);
        buffer.append("ulServerRandomLen: ");
        buffer.append(pServerRandom.length);
        //buffer.append(Constants.NEWLINE);

        return buffer.toString();
    }

}
