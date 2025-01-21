/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.security.krb5.internal.crypto;

import sun.security.krb5.Checksum;
import sun.security.krb5.KrbCryptoException;
import sun.security.krb5.internal.*;
import java.security.GeneralSecurityException;

/*
 * This class encapsulates the checksum type for AES256
 *
 * @author Seema Malkani
 */

public class HmacSha1Aes256CksumType extends CksumType {

    public HmacSha1Aes256CksumType() {
    }

    public int confounderSize() {
        return 16;
    }

    public int cksumType() {
        return Checksum.CKSUMTYPE_HMAC_SHA1_96_AES256;
    }

    public boolean isKeyed() {
        return true;
    }

    public int cksumSize() {
        return 12;  // bytes
    }

    public int keyType() {
        return Krb5.KEYTYPE_AES;
    }

    public int keySize() {
        return 32;   // bytes
    }

    /**
     * Calculates keyed checksum.
     * @param data the data used to generate the checksum.
     * @param size length of the data.
     * @param key the key used to encrypt the checksum.
     * @return keyed checksum.
     */
    public byte[] calculateChecksum(byte[] data, int size, byte[] key,
        int usage) throws KrbCryptoException {

         try {
            return Aes256.calculateChecksum(key, usage, data, 0, size);
         } catch (GeneralSecurityException e) {
            KrbCryptoException ke = new KrbCryptoException(e.getMessage());
            ke.initCause(e);
            throw ke;
         }
    }

    /**
     * Verifies keyed checksum.
     * @param data the data.
     * @param size the length of data.
     * @param key the key used to encrypt the checksum.
     * @param checksum the checksum.
     * @return true if verification is successful.
     */
    public boolean verifyChecksum(byte[] data, int size,
        byte[] key, byte[] checksum, int usage) throws KrbCryptoException {

         try {
            byte[] newCksum = Aes256.calculateChecksum(key, usage, data,
                                                        0, size);
            return isChecksumEqual(checksum, newCksum);
         } catch (GeneralSecurityException e) {
            KrbCryptoException ke = new KrbCryptoException(e.getMessage());
            ke.initCause(e);
            throw ke;
         }
    }
}
