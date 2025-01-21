/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package sun.security.x509;

import java.io.IOException;
import java.security.PublicKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import sun.security.util.HexDumpEncoder;
import sun.security.util.*;

/**
 * Represent the Key Identifier ASN.1 object.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class KeyIdentifier {
    private final byte[] octetString;

    /**
     * Create a KeyIdentifier with the passed bit settings.
     *
     * @param octetString the octet string identifying the key identifier.
     */
    public KeyIdentifier(byte[] octetString) {
        this.octetString = octetString.clone();
    }

    /**
     * Create a KeyIdentifier from the DER encoded value.
     *
     * @param val the DerValue
     */
    public KeyIdentifier(DerValue val) throws IOException {
        octetString = val.getOctetString();
    }

    /**
     * Creates a KeyIdentifier from a public-key value.
     *
     * <p>From RFC 5280: Two common methods for generating key identifiers from
     * the public key are:
     * <ol>
     * <li>The keyIdentifier is composed of the 160-bit SHA-1 hash of the
     * value of the BIT STRING subjectPublicKey (excluding the tag,
     * length, and number of unused bits).
     *
     * <li>The keyIdentifier is composed of a four bit type field with
     * the value 0100 followed by the least significant 60 bits of the
     * SHA-1 hash of the value of the BIT STRING subjectPublicKey.
     * </ol>
     * <p>This method supports method 1.
     *
     * @param pubKey the public key from which to construct this KeyIdentifier
     * @throws IOException on parsing errors
     */
    public KeyIdentifier(PublicKey pubKey)
        throws IOException
    {
        DerValue algAndKey = new DerValue(pubKey.getEncoded());
        if (algAndKey.tag != DerValue.tag_Sequence)
            throw new IOException("PublicKey value is not a valid "
                                  + "X.509 public key");

        AlgorithmId algid = AlgorithmId.parse(algAndKey.data.getDerValue());
        byte[] key = algAndKey.data.getUnalignedBitString().toByteArray();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e3) {
            throw new IOException("SHA1 not supported");
        }
        md.update(key);
        this.octetString = md.digest();
    }

    /**
     * Return the value of the KeyIdentifier as byte array.
     */
    public byte[] getIdentifier() {
        return octetString.clone();
    }

    /**
     * Returns a printable representation of the KeyUsage.
     */
    public String toString() {
        String s = "KeyIdentifier [\n";

        HexDumpEncoder encoder = new HexDumpEncoder();
        s += encoder.encodeBuffer(octetString);
        s += "]\n";
        return (s);
    }

    /**
     * Write the KeyIdentifier to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the object to.
     * @exception IOException
     */
    void encode(DerOutputStream out) {
        out.putOctetString(octetString);
    }

    /**
     * Returns a hash code value for this object.
     * Objects that are equal will also have the same hashcode.
     */
    @Override
    public int hashCode () {
        return Arrays.hashCode(octetString);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return obj instanceof KeyIdentifier other
                && Arrays.equals(octetString, other.octetString);
    }
}
