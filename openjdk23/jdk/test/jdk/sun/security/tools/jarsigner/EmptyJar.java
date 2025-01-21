/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.nio.file.Path;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

import jdk.test.lib.util.JarUtils;
import jdk.test.lib.SecurityTools;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.testng.Assert.*;

/**
 * @test
 * @bug 8217375
 * @modules java.base/sun.security.util
 * @library /test/lib /lib/testlibrary
 * @run testng EmptyJar
 * @summary Checks that signing an empty jar file does not result in an NPE or
 * other error condition.
 */
public class EmptyJar {

    static final String KEYSTORE_FILENAME = "test.jks";

    @BeforeClass
    public void prepareKeyStore() throws Exception {
        SecurityTools.keytool("-genkeypair -keyalg EC -keystore "
                + KEYSTORE_FILENAME + " -storepass changeit -keypass changeit"
                + " -alias a -dname CN=A").shouldHaveExitValue(0);
    }

    @Test
    public void test() throws Exception {
        String jarFilename = "test.jar";
        JarUtils.createJarFile(Path.of(jarFilename), (Manifest) null,
                Path.of("."));
        SecurityTools.jarsigner("-keystore " + KEYSTORE_FILENAME +
                " -storepass changeit -verbose -debug " + jarFilename + " a")
                .shouldHaveExitValue(0);

        // verify that jarsigner has added a default manifest
        byte[] mfBytes = Utils.readJarManifestBytes(jarFilename);
        Utils.echoManifest(mfBytes, "manifest");
        assertTrue(new String(mfBytes, UTF_8).startsWith(
                Name.MANIFEST_VERSION + ": 1.0\r\nCreated-By: "));
    }

}
