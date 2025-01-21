/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4404718
 * @summary Make sure that a CertPath object can be serialized
 */
import java.io.*;
import java.security.cert.*;
import java.util.Collections;

public class Serialize {

    public static void main(String args[]) throws Exception {

        // create a certpath consisting of one certificate
        File f = new File(System.getProperty("test.src", "."), "cert_file");
        FileInputStream fis = new FileInputStream(f);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate c = cf.generateCertificate(fis);
        fis.close();
        CertPath outcp = cf.generateCertPath(Collections.singletonList(c));

        // serialize certpath and write it out
        FileOutputStream fos = new FileOutputStream("certpath.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(outcp);
        oos.flush();
        oos.close();
        fos.close();

        // read certpath in and deserialize
        FileInputStream cfis = new FileInputStream("certpath.ser");
        ObjectInputStream ois = new ObjectInputStream(cfis);
        CertPath incp = (CertPath)ois.readObject();
        ois.close();
        cfis.close();

        if (!incp.equals(outcp))
            throw new Exception("CertPath serialization test FAILED");
    }
}
