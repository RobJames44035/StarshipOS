/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.jvm;

import java.nio.file.Paths;

import jdk.jfr.Configuration;
import jdk.jfr.Recording;
import jdk.jfr.internal.JVMSupport;

/**
 * @test
 * @summary Checks that the JVM can rollback on native initialization failures.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules jdk.jfr/jdk.jfr.internal
 * @run main/othervm jdk.jfr.jvm.TestCreateNative
 */
public class TestCreateNative {

    // This is a white-box test where we fabricate a native initialization
    // error by calling JMV#createNative(false), which will tear down
    // all native structures after they are setup, as if something went wrong
    // at the last step.
    public static void main(String... args) throws Exception {
        // Ensure that repeated failures can be handled
        for (int i = 1; i < 4; i++) {
            System.out.println("About to try failed initialization, attempt " + i + " out of 3");
            assertFailedInitialization();
            System.out.println("As expected, initialization failed.");
        }
        // Ensure that Flight Recorder can be initialized properly after failures
        Configuration defConfig = Configuration.getConfiguration("default");
        Recording r = new Recording(defConfig);
        r.start();
        r.stop();
        r.dump(Paths.get("recording.jfr"));
        r.close();
    }

    private static void assertFailedInitialization() throws Exception {
        try {
            JVMSupport.createFailedNativeJFR();
            throw new Exception("Expected failure when creating native JFR");
        } catch (IllegalStateException ise) {
            String message = ise.getMessage();
            if (!message.equals("Unable to start Jfr")) {
                throw new Exception("Expected failure on initialization of native JFR");
            }
        }
    }
}
