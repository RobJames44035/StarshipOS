/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/* @test
 * @bug 5070773
 * @summary SunPKCS11 provider does not support spaces config's provider name
 * @library /test/lib ..
 * @run testng/othervm ConfigQuotedString
 */

import jtreg.SkippedException;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.security.*;

public class ConfigQuotedString extends PKCS11Test {

    @BeforeClass
    public void setUp() throws Exception {
        Path configPath = Path.of(BASE).resolve("ConfigQuotedString-nss.txt");
        System.setProperty("CUSTOM_P11_CONFIG", configPath.toString());
    }

    @Test
    public void testQuotedString() throws Exception {
        try {
            main(new ConfigQuotedString());
        } catch (SkippedException se) {
            throw new SkipException("One or more tests are skipped");
        }
    }

    public void main(Provider p) throws Exception {
        System.out.println(p);
        System.out.println("test passed");
    }
}
