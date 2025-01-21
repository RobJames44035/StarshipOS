/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */


import java.nio.file.Path;

import bootreporter.*;
import jdk.test.lib.helpers.ClassFileInstaller;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @bug 6263319 8334167
 * @summary test setNativeMethodPrefix
 * @requires ((vm.opt.StartFlightRecording == null) | (vm.opt.StartFlightRecording == false)) & ((vm.opt.FlightRecorder == null) | (vm.opt.FlightRecorder == false))
 * @modules java.instrument
 * @library /test/lib
 * @build bootreporter.StringIdCallback bootreporter.StringIdCallbackReporter
 *        asmlib.Instrumentor NativeMethodPrefixAgent
 * @run main/native NativeMethodPrefixApp roleDriver
 */
public class NativeMethodPrefixApp implements StringIdCallback {

    // we expect this native method, which is part of this test's application,
    // to be instrumented and invoked
    static String goldenNativeMethodName = "fooBarNativeMethod";
    static boolean[] gotIt = {false, false, false};
    private static final String testLibraryPath = System.getProperty("test.nativepath");

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            if (!"roleDriver".equals(args[0])) {
                throw new Exception("unexpected program argument: " + args[0]);
            }
            // launch the NativeMethodPrefixApp java process after creating the necessary
            // infrastructure
            System.out.println("creating agent jar");
            final Path agentJar = createAgentJar();
            System.out.println("launching app, with javaagent jar: " + agentJar);
            launchApp(agentJar);
        } else {
            System.err.println("running app");
            System.loadLibrary("NativeMethodPrefix"); // load the native library
            new NativeMethodPrefixApp().run();
        }
    }

    private static Path createAgentJar() throws Exception {
        final Path agentJar = Path.of("NativeMethodPrefixAgent.jar");
        final String manifest = """
                Manifest-Version: 1.0
                Premain-Class: NativeMethodPrefixAgent
                Can-Retransform-Classes: true
                Can-Set-Native-Method-Prefix: true
                """;
        System.out.println("Manifest is:\n" + manifest);
        // create the agent jar
        ClassFileInstaller.writeJar(agentJar.getFileName().toString(),
                ClassFileInstaller.Manifest.fromString(manifest),
                "NativeMethodPrefixAgent",
                "asmlib.Instrumentor");
        return agentJar;
    }

    private static void launchApp(final Path agentJar) throws Exception {
        final OutputAnalyzer oa = ProcessTools.executeTestJava(
                "-javaagent:" + agentJar.toString(),
                "-Djava.library.path=" + testLibraryPath,
                NativeMethodPrefixApp.class.getName());
        oa.shouldHaveExitValue(0);
        // make available stdout/stderr in the logs, even in case of successful completion
        oa.reportDiagnosticSummary();
    }

    private void run() throws Exception {
        StringIdCallbackReporter.registerCallback(this);
        System.err.println("start");
        final long val = new Dummy().callSomeNativeMethod();
        if (val != 42) {
            throw new RuntimeException("unexpected return value " + val
                    + " from native method, expected 42");
        }

        NativeMethodPrefixAgent.checkErrors();

        for (int i = 0; i < gotIt.length; ++i) {
            if (!gotIt[i]) {
                throw new Exception("ERROR: Missing callback for transform " + i);
            }
        }
    }

    @Override
    public void tracker(String name, int id) {
        if (name.endsWith(goldenNativeMethodName)) {
            System.err.println("Tracked #" + id + ": MATCHED -- " + name);
            gotIt[id] = true;
        } else {
            System.err.println("Tracked #" + id + ": " + name);
        }
    }

    private static class Dummy {

        private long callSomeNativeMethod() {
            return fooBarNativeMethod();
        }

        private native long fooBarNativeMethod();
    }
}
