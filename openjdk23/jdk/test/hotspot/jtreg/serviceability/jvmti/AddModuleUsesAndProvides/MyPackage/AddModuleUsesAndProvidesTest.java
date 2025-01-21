/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Basic test for JVMTI AddModuleUses and AddModuleProvides
 * @requires vm.jvmti
 * @build java.base/java.lang.TestProvider
 *        java.base/jdk.internal.test.TestProviderImpl
 * @compile AddModuleUsesAndProvidesTest.java
 * @run main/othervm/native -agentlib:AddModuleUsesAndProvidesTest MyPackage.AddModuleUsesAndProvidesTest
 */

import java.io.PrintStream;
import java.lang.TestProvider;

public class AddModuleUsesAndProvidesTest {

    static {
        try {
            System.loadLibrary("AddModuleUsesAndProvidesTest");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load AddModuleUsesAndProvidesTest library");
            System.err.println("java.library.path: "
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int checkUses(Module baseModule, Class<?> service);
    native static int checkProvides(Module baseModule, Class<?> service, Class<?> serviceImpl);

    public static void main(String args[]) throws Exception {
        Module baseModule = Object.class.getModule();
        Class<?> service = TestProvider.class;
        Class<?> serviceImpl = Class.forName("jdk.internal.test.TestProviderImpl");

        System.out.println("\n*** Checks for JVMTI AddModuleUses ***\n");

        int status = checkUses(baseModule, service);
        if (status != 0) {
            throw new RuntimeException("Non-zero status returned from the agent: " + status);
        }

        System.out.println("\n*** Checks for JVMTI AddModuleProvides ***\n");

        System.out.println("Check #PC1:");
        if (TestProvider.providers().iterator().hasNext()) {
            throw new RuntimeException("Check #PC1: Unexpectedly service is provided");
        }

        status = checkProvides(baseModule, service, serviceImpl);
        if (status != 0) {
            throw new RuntimeException("Non-zero status returned from the agent: " + status);
        }

        System.out.println("Check #PC3:");
        if (!TestProvider.providers().iterator().hasNext()) {
            throw new RuntimeException("Check #PC3: Unexpectedly service is not provided");
        }
    }
}
