/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * The certificates and corresponding private keys.
 */
public class Cert {

    public final KeyAlgorithm keyAlgo;
    public final SignatureAlgorithm sigAlgo;
    public final HashAlgorithm hashAlgo;

    public final String certMaterials;
    public final String keyMaterials;

    public Cert(
            KeyAlgorithm keyAlgo,
            SignatureAlgorithm sigAlgo,
            HashAlgorithm hashAlgo,
            String certMaterials,
            String keyMaterials) {
        this.keyAlgo = keyAlgo;
        this.sigAlgo = sigAlgo;
        this.hashAlgo = hashAlgo;

        this.certMaterials = certMaterials;
        this.keyMaterials = keyMaterials;
    }

    public Cert(
            KeyAlgorithm keyAlgo,
            SignatureAlgorithm sigAlgo,
            HashAlgorithm hashAlgo,
            String certMaterials) {
        this(keyAlgo, sigAlgo, hashAlgo, certMaterials, null);
    }

    @Override
    public String toString() {
        return "keyAlgo=" + keyAlgo
                + ",sigAlgo=" + sigAlgo
                + ",hashAlg=" + hashAlgo;
    }
}
