/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 0000000
 * @library /test/lib
 * @summary DHKeyGenSpeed
 * @author Jan Luehe
 */
import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.math.*;
import jdk.test.lib.security.DiffieHellmanGroup;
import jdk.test.lib.security.SecurityUtils;

public class DHKeyGenSpeed {

    public static void main(String[] args) throws Exception {
        DHKeyGenSpeed test = new DHKeyGenSpeed();
        test.run();
        System.out.println("Test Passed");
    }

    public void run() throws Exception {
        long start, end;

        DiffieHellmanGroup dhGroup = SecurityUtils.getTestDHGroup();
        BigInteger p = dhGroup.getPrime();
        BigInteger g = new BigInteger(1, dhGroup.getBase().toByteArray());
        int l = 576;

        DHParameterSpec spec =
            new DHParameterSpec(p, g, l);

        // generate keyPairs using parameters
        KeyPairGenerator keyGen =
            KeyPairGenerator.getInstance("DH",
                    System.getProperty("test.provider.name", "SunJCE"));
        start = System.currentTimeMillis();
        keyGen.initialize(spec);
        KeyPair keys = keyGen.generateKeyPair();
        end = System.currentTimeMillis();

        System.out.println("PrimeBits\tExponentBits");
        System.out.println(dhGroup.getPrime().bitLength() + "\t\t" + l);
        System.out.println("keyGen(millisecond): " + (end - start));
        System.out.println("Test Passed!");
    }
}
