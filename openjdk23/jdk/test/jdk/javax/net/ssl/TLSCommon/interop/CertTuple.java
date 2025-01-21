/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * A tuple for carrying certificates.
 */
public class CertTuple {

    // Trusted CAs
    public final Cert[] trustedCerts;

    // End entity certificates
    public final Cert[] endEntityCerts;

    public CertTuple(Cert[] trustedCerts, Cert[] endEntityCerts) {
        this.trustedCerts = trustedCerts;
        this.endEntityCerts = endEntityCerts;
    }

    public CertTuple(Cert trustedCert, Cert endEntityCert) {
        this.trustedCerts = new Cert[] { trustedCert };
        this.endEntityCerts = new Cert[] { endEntityCert };
    }

    @Override
    public String toString() {
        return Utilities.join(Utilities.PARAM_DELIMITER,
                "trustedCerts=" + Utilities.join(trustedCerts),
                "endEntityCerts=" + Utilities.join(endEntityCerts));
    }
}
