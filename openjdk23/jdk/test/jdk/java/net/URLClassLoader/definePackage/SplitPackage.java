/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8153665
 * @summary Test two URLClassLoader define Package object of the same name
 * @library /test/lib
 * @build jdk.test.lib.compiler.CompilerUtils
 * @modules jdk.compiler
 * @run testng SplitPackage
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.jar.Manifest;
import jdk.test.lib.compiler.CompilerUtils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class SplitPackage {
    private static final Path SRC_DIR = Paths.get(System.getProperty("test.src", "."));
    private static final Path FOO_DIR = Paths.get("foo");
    private static final Path BAR_DIR = Paths.get("bar");

    @BeforeTest
    private void setup() throws Exception {
        Files.createDirectory(BAR_DIR);

        Path pkgDir = Paths.get("p");
        // compile classes in package p
        assertTrue(CompilerUtils.compile(SRC_DIR.resolve(pkgDir), BAR_DIR));

        // move p.Foo to a different directory
        Path foo = pkgDir.resolve("Foo.class");
        Files.createDirectories(FOO_DIR.resolve(pkgDir));
        Files.move(BAR_DIR.resolve(foo), FOO_DIR.resolve(foo),
                   StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void test() throws Exception {
        URLClassLoader loader1 = new URLClassLoader(new URL[] {
            FOO_DIR.toUri().toURL()
        });
        Loader loader2 = new Loader(new URL[] {
            BAR_DIR.toUri().toURL()
        }, loader1);

        Class<?> foo = Class.forName("p.Foo", true, loader2);
        Class<?> bar = Class.forName("p.Bar", true, loader2);
        Class<?> baz = Class.forName("p.Baz", true, loader2);

        Package pForFoo = loader1.getDefinedPackage("p");
        Package pForBar = loader2.getDefinedPackage("p");

        assertEquals(pForFoo.getName(), pForBar.getName());
        assertTrue(pForFoo != pForBar);

        try {
            loader2.defineSplitPackage("p");
        } catch (IllegalArgumentException e) {

        }
    }

    static class Loader extends URLClassLoader {
        Loader(URL[] urls, URLClassLoader parent) {
            super(urls, parent);
        }

        public Package defineSplitPackage(String name) {
            Manifest manifest = new Manifest();
            return super.definePackage(name, manifest, null);
        }
    }
}

