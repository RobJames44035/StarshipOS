/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8245487
 * @summary Check that if the VM rejects classes from packages starting with "java/", it will exit
 *          cleanly after InstanceKlass::verify_on(), and not leave freed memory in _local_interfaces.
 * @requires vm.flagless
 * @library /test/lib
 * @compile BadClassPackage.jasm
 * @run driver TestBadPackageWithInterface
 */

import java.io.InputStream;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestBadPackageWithInterface {
    public static void main(String args[]) throws Throwable {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-cp", System.getProperty("test.classes"),
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+VerifyBeforeExit", MyLoader.class.getName());
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldHaveExitValue(0);

    }

    static class MyLoader extends ClassLoader {
        public static void main(String args[]) throws Throwable {
            try {
                ClassLoader loader = TestBadPackageWithInterface.class.getClassLoader();
                InputStream in = loader.getResourceAsStream("java/lang/BadClassPackage.class");
                byte[] bytes = in.readAllBytes();

                MyLoader myLoader = new MyLoader();
                myLoader.defineClass(bytes, 0, bytes.length);
            }
            catch (SecurityException expected) {
                System.out.println("Expected ==================================================");
                expected.printStackTrace(System.out);
                System.out.println("==================================================");
            }
        }
    }
}
