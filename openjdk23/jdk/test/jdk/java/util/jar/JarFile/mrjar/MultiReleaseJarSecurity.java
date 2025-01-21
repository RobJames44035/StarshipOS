/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8132734 8144062
 * @summary Test potential security related issues
 * @library /lib/testlibrary/java/util/jar /test/lib/
 * @build CreateMultiReleaseTestJars
 *        jdk.test.lib.compiler.Compiler
 *        jdk.test.lib.util.JarBuilder
 * @run testng MultiReleaseJarSecurity
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MultiReleaseJarSecurity {

    static final int MAJOR_VERSION = Runtime.version().major();

    String userdir = System.getProperty("user.dir",".");
    File multirelease = new File(userdir, "multi-release.jar");
    File signedmultirelease = new File(userdir, "signed-multi-release.jar");

    @BeforeClass
    public void initialize() throws Exception {
        CreateMultiReleaseTestJars creator =  new CreateMultiReleaseTestJars();
        creator.compileEntries();
        creator.buildMultiReleaseJar();
        creator.buildSignedMultiReleaseJar();
    }

    @AfterClass
    public void close() throws IOException {
        Files.delete(multirelease.toPath());
        Files.delete(signedmultirelease.toPath());
    }

    @Test
    public void testCertsAndSigners() throws IOException {
        try (JarFile jf = new JarFile(signedmultirelease, true, ZipFile.OPEN_READ, Runtime.version())) {
            CertsAndSigners vcas = new CertsAndSigners(jf, jf.getJarEntry("version/Version.class"));
            CertsAndSigners rcas = new CertsAndSigners(jf, jf.getJarEntry("META-INF/versions/" + MAJOR_VERSION + "/version/Version.class"));
            Assert.assertTrue(Arrays.equals(rcas.getCertificates(), vcas.getCertificates()));
            Assert.assertTrue(Arrays.equals(rcas.getCodeSigners(), vcas.getCodeSigners()));
        }
    }

    private static class CertsAndSigners {
        final private JarFile jf;
        final private JarEntry je;
        private boolean readComplete;

        CertsAndSigners(JarFile jf, JarEntry je) {
            this.jf = jf;
            this.je = je;
        }

        Certificate[] getCertificates() throws IOException {
            readEntry();
            return je.getCertificates();
        }

        CodeSigner[] getCodeSigners() throws IOException {
            readEntry();
            return je.getCodeSigners();
        }

        private void readEntry() throws IOException {
            if (!readComplete) {
                try (InputStream is = jf.getInputStream(je)) {
                    is.readAllBytes();
                }
                readComplete = true;
            }
        }
    }
}
