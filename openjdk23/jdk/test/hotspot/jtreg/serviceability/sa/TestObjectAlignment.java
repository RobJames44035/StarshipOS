/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.util.ArrayList;
import java.util.List;

import sun.jvm.hotspot.HotSpotAgent;
import sun.jvm.hotspot.runtime.VM;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.Asserts;
import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.SA.SATestUtils;
import jdk.test.lib.Utils;

/**
 * @test
 * @library /test/lib
 * @requires vm.hasSA & vm.bits == "64"
 * @modules jdk.hotspot.agent/sun.jvm.hotspot
 *          jdk.hotspot.agent/sun.jvm.hotspot.runtime
 * @run driver TestObjectAlignment
 */

public class TestObjectAlignment {

    private static LingeredApp theApp = null;

    private static void checkAlignment(String pid, int expectedAlign) throws Exception {
        HotSpotAgent agent = new HotSpotAgent();

        try {
            agent.attach(Integer.parseInt(pid));
            int actualAlign = VM.getVM().getObjectAlignmentInBytes();
            Asserts.assertEquals(expectedAlign, actualAlign,
                                 "Address of G1HeapRegion does not match.");
        } finally {
            agent.detach();
        }
    }

    private static void createAnotherToAttach(long lingeredAppPid, int expectedAlign)
                                                         throws Exception {
        // Start a new process to attach to the lingered app
        ProcessBuilder processBuilder = ProcessTools.createLimitedTestJavaProcessBuilder(
            "--add-modules=jdk.hotspot.agent",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.runtime=ALL-UNNAMED",
            "TestObjectAlignment",
            Long.toString(lingeredAppPid),
            Integer.toString(expectedAlign)
        );
        SATestUtils.addPrivilegesIfNeeded(processBuilder);
        OutputAnalyzer SAOutput = ProcessTools.executeProcess(processBuilder);
        SAOutput.shouldHaveExitValue(0);
        System.out.println(SAOutput.getOutput());
    }

    public static void main (String... args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        if (args == null || args.length == 0) {
            for (int align = 8; align <= 256; align *= 2) {
                try {
                    theApp = new LingeredApp();
                    LingeredApp.startApp(theApp, "-XX:ObjectAlignmentInBytes=" + align);
                    createAnotherToAttach(theApp.getPid(), align);
                } finally {
                    LingeredApp.stopApp(theApp);
                }
            }
        } else {
            checkAlignment(args[0], Integer.parseInt(args[1]));
        }
    }
}
