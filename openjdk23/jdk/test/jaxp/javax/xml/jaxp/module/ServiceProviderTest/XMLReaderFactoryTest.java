/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.compiler.CompilerUtils;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/*
 * @test
 * @library /test/lib
 * @run testng XMLReaderFactoryTest
 * @bug 8152912 8015099 8156119
 * @summary Tests XMLReaderFactory can work as ServiceLoader compliant, as well as backward compatible
 */

@Test
public class XMLReaderFactoryTest {
    private static final String TEST_SRC = System.getProperty("test.src");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src").resolve("xmlprovider3");
    private static final Path CLASSES_DIR = Paths.get("classes");
    private static final Path LEGACY_DIR = CLASSES_DIR.resolve("legacy");
    private static final Path SERVICE_DIR = CLASSES_DIR.resolve("service");

    // resources to copy to the class path
    private static final String LEGACY_SERVICE_FILE = "legacy/META-INF/services/org.xml.sax.driver";
    private static final String SERVICE_FILE = "service/META-INF/services/org.xml.sax.XMLReader";

    /*
     * Compile class and copy service files
     */
    @BeforeTest
    public void setup() throws Exception {
        setup(LEGACY_DIR, LEGACY_SERVICE_FILE);
        setup(SERVICE_DIR, SERVICE_FILE);
    }

    private void setup(Path dest, String serviceFile) throws Exception {
        Files.createDirectories(dest);
        assertTrue(CompilerUtils.compile(SRC_DIR, dest));

        Path file = Paths.get(serviceFile.replace('/', File.separatorChar));
        Path source = SRC_DIR.resolve(file);
        Path target = CLASSES_DIR.resolve(file);
        Files.createDirectories(target.getParent());
        Files.copy(source, target);

    }

    public void testService() throws Exception {
        ClassLoader clBackup = Thread.currentThread().getContextClassLoader();
        try {
            URL[] classUrls = { SERVICE_DIR.toUri().toURL() };
            URLClassLoader loader = new URLClassLoader(classUrls, ClassLoader.getSystemClassLoader().getParent());

            // set TCCL and try locating the provider
            Thread.currentThread().setContextClassLoader(loader);
            XMLReader reader = XMLReaderFactory.createXMLReader();
            assertEquals(reader.getClass().getName(), "xp3.XMLReaderImpl");
        } finally {
            Thread.currentThread().setContextClassLoader(clBackup);
        }
    }

    public void testLegacy() throws Exception {
        ClassLoader clBackup = Thread.currentThread().getContextClassLoader();
        try {
            URL[] classUrls = { LEGACY_DIR.toUri().toURL() };
            URLClassLoader loader = new URLClassLoader(classUrls, ClassLoader.getSystemClassLoader().getParent());

            // set TCCL and try locating the provider
            Thread.currentThread().setContextClassLoader(loader);
            XMLReader reader1 = XMLReaderFactory.createXMLReader();
            assertEquals(reader1.getClass().getName(), "xp3.XMLReaderImpl");

            // now point to a random URL
            Thread.currentThread().setContextClassLoader(
                    new URLClassLoader(new URL[0], ClassLoader.getSystemClassLoader().getParent()));
            // ClassNotFoundException if also trying to load class of reader1, which
            // would be the case before 8152912
            XMLReader reader2 = XMLReaderFactory.createXMLReader();
            assertEquals(reader2.getClass().getName(), "com.sun.org.apache.xerces.internal.parsers.SAXParser");
        } finally {
            Thread.currentThread().setContextClassLoader(clBackup);
        }
    }
}
