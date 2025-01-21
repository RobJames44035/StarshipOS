/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/*
 * @test
 * @bug 8194960
 * @summary Sanity check trust manager defaults/cacerts.
 */

/**
 * Explores the set of root certificates.
 * Also useful as a standalone program.
 *
 * Prior to JEP 319, stock openjdk fails this because no root
 * certificates were checked into the repo.
 */
public class CacertsExplorer {
    public static void main(String[] args) throws Throwable {
        String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        if (!defaultAlgorithm.equals("PKIX")) {
            throw new AssertionError(
                "Expected default algorithm PKIX, got " + defaultAlgorithm);
        }

        TrustManagerFactory trustManagerFactory =
            TrustManagerFactory.getInstance(defaultAlgorithm);
        trustManagerFactory.init((KeyStore) null);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1) {
            throw new AssertionError(
                "Expected exactly one TrustManager, got "
                + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

        X509Certificate[] acceptedIssuers = trustManager.getAcceptedIssuers();
        if (acceptedIssuers.length == 0) {
            throw new AssertionError(
                "no accepted issuers - cacerts file configuration problem?");
        }
        Arrays.stream(acceptedIssuers)
            .map(X509Certificate::getIssuerX500Principal)
            .forEach(System.out::println);
    }
}
