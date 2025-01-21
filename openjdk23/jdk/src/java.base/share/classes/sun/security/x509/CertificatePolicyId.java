/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package sun.security.x509;

import java.io.IOException;
import java.util.Objects;

import sun.security.util.*;


/**
 * Represent the CertificatePolicyId ASN.1 object.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class CertificatePolicyId implements DerEncoder {
    private final ObjectIdentifier id;

    /**
     * Create a CertificatePolicyId with the ObjectIdentifier.
     *
     * @param id the ObjectIdentifier for the policy id.
     */
    public CertificatePolicyId(ObjectIdentifier id) {
        this.id = Objects.requireNonNull(id);
    }

    /**
     * Create the object from its Der encoded value.
     *
     * @param val the DER encoded value for the same.
     */
    public CertificatePolicyId(DerValue val) throws IOException {
        this.id = val.getOID();
    }

    /**
     * Return the value of the CertificatePolicyId as an ObjectIdentifier.
     */
    public ObjectIdentifier getIdentifier() {
        return (id);
    }

    /**
     * Returns a printable representation of the CertificatePolicyId.
     */
    public String toString() {

        return ("CertificatePolicyId: ["
                 + id.toString()
                 + "]\n");
    }

    /**
     * Write the CertificatePolicyId to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the object to.
     */
    @Override
    public void encode(DerOutputStream out) {
        out.putOID(id);
    }

    /**
     * Compares this CertificatePolicyId with another, for
     * equality. Uses ObjectIdentifier.equals() as test for
     * equality.
     *
     * @return true iff the ids are identical.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CertificatePolicyId other
                && id.equals(other.getIdentifier());
    }

    /**
     * {@return a hash code value for this object}
     */
    @Override
    public int hashCode() {
      return id.hashCode();
    }
}
