/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import sun.jvm.hotspot.HotSpotAgent;
import sun.jvm.hotspot.code.CodeCacheVisitor;
import sun.jvm.hotspot.code.CodeBlob;
import sun.jvm.hotspot.code.NMethod;
import sun.jvm.hotspot.debugger.Address;
import sun.jvm.hotspot.runtime.VM;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.SA.SATestUtils;

/**
 * @test
 * @bug 8318682
 * @summary Test decoding debug info for all nmethods in the code cache
 * @requires vm.hasSA
 * @library /test/lib
 * @modules jdk.hotspot.agent/sun.jvm.hotspot
 *          jdk.hotspot.agent/sun.jvm.hotspot.code
 *          jdk.hotspot.agent/sun.jvm.hotspot.debugger
 *          jdk.hotspot.agent/sun.jvm.hotspot.runtime
 * @run driver TestDebugInfoDecode
 */

public class TestDebugInfoDecode {

    private static LingeredApp theApp = null;

    private static void checkDecode(String pid) throws Exception {
        HotSpotAgent agent = new HotSpotAgent();

        try {
            agent.attach(Integer.parseInt(pid));

            CodeCacheVisitor v = new CodeCacheVisitor() {
                    Throwable throwable;
                    public void prologue(Address start, Address end) {
                    }
                    public void visit(CodeBlob blob) {
                        if (throwable != null) {
                            // Only report the first failure.
                            return;
                        }
                        if (blob instanceof NMethod) {
                            NMethod nm = (NMethod) blob;
                            try {
                                nm.decodeAllScopeDescs();
                            } catch (Throwable t) {
                                System.err.println("Exception while decoding debug info for " + blob);
                                throwable = t;
                                throw t;
                            }
                        }
                    }
                    public void epilogue() {
                    }
                };
            VM.getVM().getCodeCache().iterate(v);
        } finally {
            agent.detach();
        }
    }

    private static void createAnotherToAttach(long lingeredAppPid) throws Exception {
        // Start a new process to attach to the lingered app
        ProcessBuilder processBuilder = ProcessTools.createLimitedTestJavaProcessBuilder(
            "--add-modules=jdk.hotspot.agent",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.code=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.debugger=ALL-UNNAMED",
            "--add-exports=jdk.hotspot.agent/sun.jvm.hotspot.runtime=ALL-UNNAMED",
            "TestDebugInfoDecode",
            Long.toString(lingeredAppPid)
        );
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
                LingeredApp.startApp(theApp, "-Xcomp");
                createAnotherToAttach(theApp.getPid());
            } finally {
                LingeredApp.stopApp(theApp);
            }
        } else {
            checkDecode(args[0]);
        }
    }
}
