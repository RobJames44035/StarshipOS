/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *          jdk.internal.vm.ci/jdk.vm.ci.services
 *
 * @build compiler.jvmci.common.JVMCIHelpers
 *        compiler.jvmci.events.JvmciShutdownEventListener
 * @run main/othervm jdk.test.lib.FileInstaller ./JvmciShutdownEventTest.config
 *     ./META-INF/services/jdk.vm.ci.services.JVMCIServiceLocator
 * @run main/othervm jdk.test.lib.helpers.ClassFileInstaller
 *      compiler.jvmci.common.JVMCIHelpers$EmptyHotspotCompiler
 *      compiler.jvmci.common.JVMCIHelpers$EmptyCompilerFactory
 *      compiler.jvmci.common.JVMCIHelpers$EmptyCompilationRequestResult
 *      compiler.jvmci.common.JVMCIHelpers$EmptyVMEventListener
 *      compiler.jvmci.events.JvmciShutdownEventListener
 * @run main/othervm -XX:+UnlockExperimentalVMOptions
 *      compiler.jvmci.events.JvmciShutdownEventTest
 */

package compiler.jvmci.events;

import jdk.test.lib.process.ExitCode;
import jdk.test.lib.cli.CommandLineOptionTest;

public class JvmciShutdownEventTest {
    private final static String[] MESSAGE = new String[]{
        JvmciShutdownEventListener.MESSAGE
    };

    private final static String[] ERROR_MESSAGE = new String[]{
        JvmciShutdownEventListener.GOT_INTERNAL_ERROR
    };

    public static void main(String args[]) throws Throwable {
        boolean addTestVMOptions = true;
        CommandLineOptionTest.verifyJVMStartup(MESSAGE, ERROR_MESSAGE,
                "Unexpected exit code with +EnableJVMCI",
                "Unexpected output with +EnableJVMCI", ExitCode.OK,
                addTestVMOptions, "-XX:+UnlockExperimentalVMOptions",
                "-XX:+EnableJVMCI", "-XX:-UseJVMCICompiler", "-Xbootclasspath/a:.",
                JvmciShutdownEventListener.class.getName()
        );

        CommandLineOptionTest.verifyJVMStartup(ERROR_MESSAGE, MESSAGE,
                "Unexpected exit code with -EnableJVMCI",
                "Unexpected output with -EnableJVMCI", ExitCode.OK,
                addTestVMOptions, "-XX:+UnlockExperimentalVMOptions",
                "-XX:-EnableJVMCI", "-XX:-UseJVMCICompiler", "-Xbootclasspath/a:.",
                JvmciShutdownEventListener.class.getName()
        );
    }
}
