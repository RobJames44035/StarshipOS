/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 5006629
 * @summary Kerberos library should only select keys of types that it supports
 */

import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.kerberos.KeyTab;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UnsupportedKeyType {

    // Homemade keytab files:
    //
    // String   KVNO Timestamp      Principal (etype)
    // -------- ---- -------------- -----------------------
    // camellia    4 4/3/14 9:58 AM u1@K1 (25:camellia128-cts-cmac)
    // aes         5 4/3/14 9:58 AM u1@K1 (17:aes128-cts-hmac-sha1-96)

    static String aes =
            "050200000027000100024b310002753100000001533cc04f0500110010e0eab6" +
            "7f31608df2b2f8fffc6b21cc91";
    static String camellia =
            "050200000027000100024b310002753100000001533cc03e0400190010d88678" +
            "14e478b6b7d2d97375163b971e";

    public static void main(String[] args) throws Exception {

        byte[] data = new byte[aes.length()/2];
        KerberosPrincipal kp = new KerberosPrincipal("u1@K1");

        // aes128
        for (int i=0; i<data.length; i++) {
            data[i] = Integer.valueOf(
                    aes.substring(2*i,2*i+2), 16).byteValue();
        }
        Files.write(Paths.get("aes"), data);
        if(KeyTab.getInstance(kp, new File("aes")).getKeys(kp).length == 0) {
            throw new Exception("AES key not read");
        }

        // camellia128
        for (int i=0; i<data.length; i++) {
            data[i] = Integer.valueOf(
                    camellia.substring(2*i,2*i+2), 16).byteValue();
        }
        Files.write(Paths.get("camellia"), data);
        if(KeyTab.getInstance(kp, new File("camellia")).getKeys(kp).length != 0) {
            throw new Exception("Unknown key read");
        }
    }
}
