/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * Signature algorithms.
 */
public enum SignatureAlgorithm {

    RSA("RSA"),
    DSA("DSA"),
    ECDSA("ECDSA"),
    RSASSAPSS("RSASSA-PSS");

    public final String name;

    private SignatureAlgorithm(String name) {
        this.name = name;
    }
}
