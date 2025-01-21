/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6813340
 * @summary X509Factory should not depend on is.available()==0
 */
import java.io.*;
import java.security.cert.*;

/**
 * Tests ol'Mac style file, end witha  single '\r'
 */
public class ReturnStream {

    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream(new File(new File(
                System.getProperty("test.src", "."), "openssl"), "pem"));
        byte[] buffer = new byte[4096];
        int size = 0;
        while (true) {
            int len = fin.read(buffer, size, 4096-size);
            if (len < 0) break;
            size += len;
        }
        fin.close();

        // Make a copy
        System.arraycopy(buffer, 0, buffer, size, size);
        size += size;

        // Create a ol'Mac style file.
        for (int i=0; i<size; i++) {
            if (buffer[i] == '\n') buffer[i] = '\r';
        }

        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        if (factory.generateCertificates(
                new ByteArrayInputStream(buffer, 0, size)).size() != 2) {
            throw new Exception("Cert not OK");
        }
    }
}
