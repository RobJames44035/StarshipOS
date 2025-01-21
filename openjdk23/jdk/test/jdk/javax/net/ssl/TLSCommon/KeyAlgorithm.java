/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * Key algorithms.
 */
public enum KeyAlgorithm {

    DSA("DSA"),
    RSA("RSA"),
    EC("EC"),
    RSASSAPSS("RSASSA-PSS");

    public final String name;

    private KeyAlgorithm(String name) {
        this.name = name;
    }
}
