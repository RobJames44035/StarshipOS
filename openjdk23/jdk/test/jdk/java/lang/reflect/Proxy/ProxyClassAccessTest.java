/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import jdk.test.lib.compiler.CompilerUtils;
import static jdk.test.lib.process.ProcessTools.executeTestJava;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @test
 * @library /test/lib
 * @modules jdk.compiler
 * @build ProxyClassAccessTest q.NP
 *        jdk.test.lib.compiler.CompilerUtils
 * @run testng ProxyClassAccessTest
 * @summary Driver for testing proxy class doesn't have access to
 *          types referenced by proxy interfaces
 */

public class ProxyClassAccessTest {

    private static final String TEST_SRC = System.getProperty("test.src");
    private static final String TEST_CLASSES = System.getProperty("test.classes");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path MODS_DIR = Paths.get("mods");

    // the names of the modules in this test
    private static List<String> modules = Arrays.asList("m1", "m2", "m3", "test");

    /**
     * Compiles all modules used by the test
     */
    @BeforeTest
    public void compileAll() throws Exception {
        for (String mn : modules) {
            Path msrc = SRC_DIR.resolve(mn);
            assertTrue(CompilerUtils.compile(msrc, MODS_DIR, "--module-source-path", SRC_DIR.toString()));
        }
    }

    /**
     * Run the modular test
     */
    @Test
    public void runTest() throws Exception {
        int exitValue = executeTestJava("--module-path", MODS_DIR.toString(),
                                        "-m", "test/jdk.test.ProxyClassAccess")
                            .outputTo(System.out)
                            .errorTo(System.out)
                            .getExitValue();

        assertTrue(exitValue == 0);
    }

    /**
     * Test unnamed module has no access to other proxy interface
     */
    @Test
    public void testNoReadAccess() throws Throwable {
        ModuleFinder finder = ModuleFinder.of(MODS_DIR);
        ModuleLayer bootLayer = ModuleLayer.boot();
        Configuration cf = bootLayer
                .configuration()
                .resolveAndBind(ModuleFinder.of(), finder, modules);
        ClassLoader parentLoader = this.getClass().getClassLoader();
        ModuleLayer layer = bootLayer.defineModulesWithOneLoader(cf, parentLoader);

        ClassLoader loader = layer.findLoader("m1");
        Class<?>[] interfaces = new Class<?>[] {
                Class.forName("p.one.I", false, loader),
                Class.forName("q.NP", false, loader)     // non-public interface in unnamed module
        };
        checkIAE(loader, interfaces);
    }

    private void checkIAE(ClassLoader loader, Class<?>[] interfaces)  throws Throwable {
        try {
            Proxy.getProxyClass(loader, interfaces);
            throw new RuntimeException("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {}

        try {
            Proxy.newProxyInstance(loader, interfaces,
                (proxy, m, params) -> { throw new RuntimeException(m.toString()); });
            throw new RuntimeException("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {}
    }

}
