/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8209005 8209078
 * @library /test/lib
 * @build m1/* FindSpecialTest
 * @run testng/othervm FindSpecialTest
 * @summary Test findSpecial and unreflectSpecial of the declaring class
 *          of the method and the special caller are not in the same module
 *          as the lookup class.
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static jdk.test.lib.process.ProcessTools.*;

import org.testng.annotations.Test;

public class FindSpecialTest {
    static final String TEST_CLASSES = System.getProperty("test.classes", ".");
    static final String TEST_CLASS_PATH = System.getProperty("test.class.path");
    static final String TEST_MAIN_CLASS = "test.FindSpecial";
    static final String TEST_MODULE = "m1";

    /*
     * Run test.FindSpecial in unnamed module
     */
    @Test
    public static void callerInUnnamedModule() throws Throwable {
        Path m1 = Paths.get(TEST_CLASSES, "modules", TEST_MODULE);
        if (Files.notExists(m1)) {
            throw new Error(m1 + " not exist");
        }
        String classpath = m1.toString() + File.pathSeparator + TEST_CLASS_PATH;
        executeCommand(createTestJavaProcessBuilder("-cp", classpath,
                                                    TEST_MAIN_CLASS))
                .shouldHaveExitValue(0);
    }

    /*
     * Run test.FindSpecial in a named module
     */
    @Test
    public static void callerInNamedModule() throws Throwable {
        Path modules = Paths.get(TEST_CLASSES, "modules");
        if (Files.notExists(modules)) {
            throw new Error(modules + " not exist");
        }
        executeCommand(createTestJavaProcessBuilder("-cp", TEST_CLASS_PATH,
                                                    "-p", modules.toString(),
                                                    "-m", TEST_MODULE + "/" + TEST_MAIN_CLASS))
                .shouldHaveExitValue(0);
    }
}
