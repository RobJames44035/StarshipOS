/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6269847
 * @summary store a NSS PKCS11 PrivateKeyEntry to JKS KeyStore throws confusing NPE
 * @author Wang Weijun
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 * @run main/othervm JksSetPrivateKey
 */

import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public class JksSetPrivateKey extends SecmodTest {

    public static void main(String[] args) throws Exception {
        if (initSecmod() == false) {
            return;
        }

        String configName = BASE + SEP + "nss.cfg";
        Provider p = getSunPKCS11(configName);

        System.out.println(p);
        Security.addProvider(p);

        KeyStore ks = KeyStore.getInstance("PKCS11", p);
        ks.load(null, password);
        Collection<String> aliases = new TreeSet<>(Collections.list(ks.aliases()));
        System.out.println("entries: " + aliases.size());
        System.out.println(aliases);

        PrivateKey privateKey = (PrivateKey)ks.getKey(keyAlias, password);
        System.out.println(privateKey);

        X509Certificate[] chain = (X509Certificate[])ks.getCertificateChain(keyAlias);

        KeyStore jks = KeyStore.getInstance("JKS");
        jks.load(null, null);

        try {
            jks.setKeyEntry("k1", privateKey, "changeit".toCharArray(), chain);
            throw new Exception("No, an NSS PrivateKey shouldn't be extractable and put inside a JKS keystore");
        } catch (KeyStoreException e) {
            System.err.println(e); // This is OK
        }

        try {
            jks.setKeyEntry("k2", new DummyPrivateKey(), "changeit".toCharArray(), chain);
            throw new Exception("No, non-PKCS#8 key shouldn't be put inside a KeyStore");
        } catch (KeyStoreException e) {
            System.err.println(e); // This is OK
        }
        System.out.println("OK");

        try {
            jks.setKeyEntry("k3", new DummyPrivateKey2(), "changeit".toCharArray(), chain);
            throw new Exception("No, not-extractble key shouldn't be put inside a KeyStore");
        } catch (KeyStoreException e) {
            System.err.println(e); // This is OK
        }
        System.out.println("OK");
    }
}

class DummyPrivateKey implements PrivateKey {
    @Override
    public String getAlgorithm() {
        return "DUMMY";
    }

    @Override
    public String getFormat() {
        return "DUMMY";
    }

    @Override
    public byte[] getEncoded() {
        return "DUMMY".getBytes();
    }
}

class DummyPrivateKey2 implements PrivateKey {
    @Override
    public String getAlgorithm() {
        return "DUMMY";
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public byte[] getEncoded() {
        return null;
    }
}
