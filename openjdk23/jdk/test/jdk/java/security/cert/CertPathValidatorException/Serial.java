/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6465942
 * @summary Test deserialization of CertPathValidatorException
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorException.BasicReason;
import java.util.Collections;

/**
 * This class tests to see if CertPathValidatorException can be serialized and
 * deserialized properly.
 */
public class Serial {
    private static volatile boolean failed = false;
    public static void main(String[] args) throws Exception {

        File f = new File(System.getProperty("test.src", "."), "cert_file");
        FileInputStream fis = new FileInputStream(f);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate c = cf.generateCertificate(fis);
        fis.close();
        CertPath cp = cf.generateCertPath(Collections.singletonList(c));

        CertPathValidatorException cpve1 =
            new CertPathValidatorException
                ("Test", new Exception("Expired"), cp, 0, BasicReason.EXPIRED);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        FileOutputStream fos = new FileOutputStream("jdk7.serial");
        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        ObjectOutputStream foos = new ObjectOutputStream(fos);
        oos.writeObject(cpve1);
//        foos.writeObject(cpve1);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        CertPathValidatorException cpve2 =
            (CertPathValidatorException) ois.readObject();
        check(!cpve1.getMessage().equals(cpve2.getMessage()),
            "CertPathValidatorException messages not equal");
        check(!cpve1.getCause().getMessage().equals(cpve2.getCause().getMessage()),
            "CertPathValidatorException causes not equal");
        check(!cpve1.getCertPath().equals(cpve2.getCertPath()),
            "CertPathValidatorException certpaths not equal");
        check(cpve1.getIndex() != cpve2.getIndex(),
            "CertPathValidatorException indexes not equal");
        check(cpve1.getReason() != cpve2.getReason(),
            "CertPathValidatorException reasons not equal");
        oos.close();
        ois.close();

        f = new File(System.getProperty("test.src", "."), "jdk6.serial");
        fis = new FileInputStream(f);
        ois = new ObjectInputStream(fis);
        cpve2 = (CertPathValidatorException) ois.readObject();
        check(!cpve1.getMessage().equals(cpve2.getMessage()),
            "CertPathValidatorException messages not equal");
        check(!cpve1.getCause().getMessage().equals(cpve2.getCause().getMessage()),
            "CertPathValidatorException causes not equal");
        check(!cpve1.getCertPath().equals(cpve2.getCertPath()),
            "CertPathValidatorException certpaths not equal");
        check(cpve1.getIndex() != cpve2.getIndex(),
            "CertPathValidatorException indexes not equal");
//      System.out.println(cpve2.getReason());
        check(cpve2.getReason() != BasicReason.UNSPECIFIED,
            "CertPathValidatorException reasons not equal");
        oos.close();
        ois.close();
        if (failed) {
            throw new Exception("Some tests FAILED");
        }
    }

    private static void check(boolean expr, String message) {
        if (expr) {
            failed = true;
            System.err.println("FAILED: " + message);
        }
    }
}
