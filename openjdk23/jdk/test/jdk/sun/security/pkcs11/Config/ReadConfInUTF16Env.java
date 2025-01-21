/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @bug 8187023
 * @summary Pkcs11 config file should be assumed in ISO-8859-1
 * @library /test/lib
 * @run testng/othervm ReadConfInUTF16Env
 */

import jdk.test.lib.process.ProcessTools;
import org.testng.annotations.Test;

import java.security.Provider;
import java.security.Security;

public class ReadConfInUTF16Env {

    @Test
    public void testReadConfInUTF16Env() throws Exception {
        String[] testCommand = new String[] { "-Dfile.encoding=UTF-16",
                TestSunPKCS11Provider.class.getName()};
        ProcessTools.executeTestJava(testCommand).shouldHaveExitValue(0);
    }

    static class TestSunPKCS11Provider {
        public static void main(String[] args) throws Exception {
            Provider p = Security.getProvider("SunPKCS11");
            if (p == null) {
                System.out.println("Skipping test - no PKCS11 provider available");
                return;
            }
            System.out.println(p.getName());
        }
    }
}
