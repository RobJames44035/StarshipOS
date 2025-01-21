/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4921802
 * @summary Test that the SunPKCS11 provider can be serialized
 * @author Andreas Sterbenz
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Provider;
import java.security.Security;

public class SerializeProvider extends PKCS11Test {

    public void main(Provider p) throws Exception {
        if (Security.getProvider(p.getName()) != p) {
            System.out.println("Provider not installed in Security, skipping");
            return;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(out);

        oout.writeObject(p);
        oout.close();

        byte[] data = out.toByteArray();

        InputStream in = new ByteArrayInputStream(data);
        ObjectInputStream oin = new ObjectInputStream(in);

        Provider p2 = (Provider)oin.readObject();

        System.out.println("Reconstituted: " + p2);

        if (p != p2) {
            throw new Exception("Provider object mismatch");
        }
    }

    public static void main(String[] args) throws Exception {
        main(new SerializeProvider());
    }

}
