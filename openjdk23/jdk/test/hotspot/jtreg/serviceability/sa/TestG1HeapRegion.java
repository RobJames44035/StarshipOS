/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.util.ArrayList;
import java.util.List;

import sun.jvm.hotspot.gc.g1.G1CollectedHeap;
import sun.jvm.hotspot.gc.g1.G1HeapRegion;
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
 * @requires vm.hasSA
 * @requires vm.gc.G1
 * @modules jdk.hotspot.agent/sun.jvm.hotspot
 *          jdk.hotspot.agent/sun.jvm.hotspot.gc.g1
 *          jdk.hotspot.agent/sun.jvm.hotspot.memory
 *          jdk.hotspot.agent/sun.jvm.hotspot.runtime
 * @run driver TestG1HeapRegion
 */

public class TestG1HeapRegion {

    private static LingeredApp theApp = null;

    private static void checkHeapRegion(String pid) throws Exception {
        HotSpotAgent agent = new HotSpotAgent();

        try {
            agent.attach(Integer.parseInt(pid));
            G1CollectedHeap heap = (G1CollectedHeap)VM.getVM().getUniverse().heap();
            G1HeapRegion hr = heap.hrm().heapRegionIterator().next();
            G1HeapRegion hrTop = heap.hrm().getByAddress(hr.top());

            Asserts.assertEquals(hr.top(), hrTop.top(),
                                 "Address of G1HeapRegion does not match.");
        } finally {
            agent.detach();
        }
    }

    private static void createAnotherToAttach(long lingeredAppPid)
                                                         throws Exception {
        // Start a new process to attach to the lingered app
        ProcessBuilder processBuilder = ProcessTools.createLimitedTestJavaProcessBuilder(
            "--add-modules=jdk.hotspot.agent",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.gc.g1=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.memory=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.runtime=ALL-UNNAMED",
            "TestG1HeapRegion",
            Long.toString(lingeredAppPid));
        SATestUtils.addPrivilegesIfNeeded(processBuilder);
        OutputAnalyzer SAOutput = ProcessTools.executeProcess(processBuilder);
        SAOutput.shouldHaveExitValue(0);
        System.out.println(SAOutput.getOutput());
    }

    public static void main (String... args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        if (args == null || args.length == 0) {
            try {
                theApp = new LingeredApp();
                LingeredApp.startApp(theApp, "-XX:+UsePerfData", "-XX:+UseG1GC");
                createAnotherToAttach(theApp.getPid());
            } finally {
                LingeredApp.stopApp(theApp);
            }
        } else {
            checkHeapRegion(args[0]);
        }
    }
}
