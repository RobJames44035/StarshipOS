/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8318328
 * @summary DHKEM should check XDH name in case-insensitive mode
 * @library /test/lib
 * @modules java.base/com.sun.crypto.provider
 */
import javax.crypto.KEM;
import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.interfaces.XECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.NamedParameterSpec;

public class NameSensitiveness {
    public static void main(String[] args) throws Exception {
        var g = KeyPairGenerator.getInstance("XDH");
        g.initialize(NamedParameterSpec.X25519);
        var pk1 = (XECPublicKey) g.generateKeyPair().getPublic();
        var pk2 = new XECPublicKey() {
            public BigInteger getU() {
                return pk1.getU();
            }
            public AlgorithmParameterSpec getParams() {
                return new NamedParameterSpec("x25519"); // lowercase!!!
            }
            public String getAlgorithm() {
                return pk1.getAlgorithm();
            }
            public String getFormat() {
                return pk1.getFormat();
            }
            public byte[] getEncoded() {
                return pk1.getEncoded();
            }
        };
        var kem = KEM.getInstance("DHKEM");
        kem.newEncapsulator(pk2);
    }
}
