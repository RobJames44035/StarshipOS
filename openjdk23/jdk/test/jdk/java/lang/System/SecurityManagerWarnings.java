/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8266459 8268349 8269543 8270380
 * @summary check various warnings
 * @library /test/lib
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.util.JarUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

public class SecurityManagerWarnings {

    public static void main(String args[]) throws Exception {
        if (args.length == 0) {

            System.setProperty("test.noclasspath", "true");
            String testClasses = System.getProperty("test.classes");

            failLateTest(null, testClasses);
            failLateTest("disallow", testClasses);
            failEarlyTest("allow", testClasses);
            failEarlyTest("", testClasses);
            failEarlyTest("default", testClasses);
            failEarlyTest("java.lang.SecurityManager", testClasses);

            JarUtils.createJarFile(Path.of("a.jar"),
                    Path.of(testClasses),
                    Path.of("SecurityManagerWarnings.class"),
                    Path.of("A.class"));

            failLateTest(null, "a.jar");
        } else {
            PrintStream oldErr = System.err;
            // Modify System.err, thus make sure warnings are always printed
            // to the original System.err and will not be swallowed.
            System.setErr(new PrintStream(new ByteArrayOutputStream()));
            try {
                A.run();    // System.setSecurityManager(null);
            } catch (Exception e) {
                // Exception messages must show in original stderr
                e.printStackTrace(oldErr);
                throw e;
            }
        }
    }

    // When -Djava.security.manager is not set, or set to "allow",
    // or "disallow", JVM starts but setSecurityManager will fail.
    static void failLateTest(String prop, String cp) throws Exception {
        run(prop, cp)
                .shouldNotHaveExitValue(0)
                .stderrShouldContain("at SecurityManagerWarnings.main")
                .stderrShouldContain("UnsupportedOperationException: Setting a Security Manager is not supported");
    }

    // When -Djava.security.manager is set to any other values,
    // JVM will not start.
    static void failEarlyTest(String prop, String cp) throws Exception {
        run(prop, cp)
                .shouldNotHaveExitValue(0)
                .shouldNotContain("SecurityManagerWarnings.main")
                .shouldContain("at java.lang.System.initPhase3")
                .shouldContain("Error: A command line option has attempted to allow or enable the Security Manager.");
    }

    static OutputAnalyzer run(String prop, String cp) throws Exception {
        ProcessBuilder pb;
        if (prop == null) {
            pb = ProcessTools.createTestJavaProcessBuilder(
                    "-cp", cp,
                    "SecurityManagerWarnings", "run");
        } else {
            pb = ProcessTools.createTestJavaProcessBuilder(
                    "-cp", cp,
                    "-Djava.security.manager=" + prop,
                    "SecurityManagerWarnings", "run");
        }
        return ProcessTools.executeProcess(pb)
                .stderrShouldNotContain("AccessControlException");
    }
}

class A {
    static void run() {
        System.setSecurityManager(null);
    }
}
