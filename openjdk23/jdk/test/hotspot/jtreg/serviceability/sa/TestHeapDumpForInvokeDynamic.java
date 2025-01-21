/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.File;
import java.util.stream.Collectors;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.Asserts;
import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.SA.SATestUtils;
import jdk.test.lib.Utils;
import jdk.test.lib.hprof.HprofParser;

/**
 * @test
 * @library /test/lib
 * @requires vm.hasSA
 * @modules java.base/jdk.internal.misc
 *          jdk.hotspot.agent/sun.jvm.hotspot
 *          jdk.hotspot.agent/sun.jvm.hotspot.utilities
 *          jdk.hotspot.agent/sun.jvm.hotspot.oops
 *          jdk.hotspot.agent/sun.jvm.hotspot.debugger
 * @run driver TestHeapDumpForInvokeDynamic
 */

public class TestHeapDumpForInvokeDynamic {

    private static LingeredAppWithInvokeDynamic theApp = null;

    private static void attachDumpAndVerify(String heapDumpFileName,
                                            long lingeredAppPid) throws Exception {

        JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jhsdb");
        launcher.addVMArgs(Utils.getTestJavaOpts());
        launcher.addToolArg("jmap");
        launcher.addToolArg("--binaryheap");
        launcher.addToolArg("--dumpfile");
        launcher.addToolArg(heapDumpFileName);
        launcher.addToolArg("--pid");
        launcher.addToolArg(Long.toString(lingeredAppPid));

        ProcessBuilder processBuilder = SATestUtils.createProcessBuilder(launcher);
        System.out.println(
            processBuilder.command().stream().collect(Collectors.joining(" ")));

        OutputAnalyzer SAOutput = ProcessTools.executeProcess(processBuilder);
        SAOutput.shouldHaveExitValue(0);
        SAOutput.shouldContain("heap written to");
        SAOutput.shouldContain(heapDumpFileName);
        System.out.println(SAOutput.getOutput());

        HprofParser.parseAndVerify(new File(heapDumpFileName));
    }

    public static void main (String... args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        String heapDumpFileName = "lambdaHeapDump.bin";

        File heapDumpFile = new File(heapDumpFileName);
        if (heapDumpFile.exists()) {
            heapDumpFile.delete();
        }

        try {
            theApp = new LingeredAppWithInvokeDynamic();
            LingeredApp.startApp(theApp, "-XX:+UsePerfData", "-Xmx512m");
            attachDumpAndVerify(heapDumpFileName, theApp.getPid());
        } finally {
            LingeredApp.stopApp(theApp);
        }
    }
}
