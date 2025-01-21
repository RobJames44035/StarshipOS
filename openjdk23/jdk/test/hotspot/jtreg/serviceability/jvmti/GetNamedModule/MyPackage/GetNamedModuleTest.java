/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies the JVMTI GetNamedModule API
 * @requires vm.jvmti
 * @compile GetNamedModuleTest.java
 * @run main/othervm/native -agentlib:GetNamedModuleTest MyPackage.GetNamedModuleTest
 */

import java.io.PrintStream;

public class GetNamedModuleTest {

    static {
        try {
            System.loadLibrary("GetNamedModuleTest");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load GetNamedModuleTest library");
            System.err.println("java.library.path: "
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check();

    public static void main(String args[]) {
        int status = check();
        if (status != 0) {
            throw new RuntimeException("Non-zero status returned from the agent: " + status);
        }
    }
}
