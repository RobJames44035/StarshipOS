/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8169909
 * @requires vm.flagless
 * @library src /test/lib
 * @build test/*
 * @run shell AppendToClassPathModuleTest.sh
 * @run main AppendToClassPathModuleTest
 */

import jdk.test.lib.JDKToolFinder;

import java.util.Map;
import java.util.stream.Stream;

import static jdk.test.lib.process.ProcessTools.*;

/**
 * Launch a modular test with no class path and no CLASSPATH set.
 * The java agent appends to the "hidden" directory to the class path
 * at runtime.
 */
public class AppendToClassPathModuleTest {
    public static void main(String... args) throws Throwable {
        String modulepath = System.getProperty("test.module.path");

        // can't use ProcessTools.createLimitedTestJavaProcessBuilder as it always adds -cp
        ProcessBuilder pb = new ProcessBuilder(
                JDKToolFinder.getTestJDKTool("java"),
                "-javaagent:Agent.jar",
                "--module-path", modulepath,
                "-m", "test/jdk.test.Main");

        Map<String,String> env = pb.environment();
        // remove CLASSPATH environment variable
        env.remove("CLASSPATH");

        int exitCode = executeCommand(pb).getExitValue();
        if (exitCode != 0) {
            throw new RuntimeException("Test failed: " + exitCode);
        }
    }

}
