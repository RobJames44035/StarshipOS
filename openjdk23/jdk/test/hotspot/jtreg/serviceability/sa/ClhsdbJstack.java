/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.test.lib.apps.LingeredApp;
import jtreg.SkippedException;

/**
 * @test
 * @bug 8190198
 * @summary Test clhsdb Jstack command
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm/timeout=480 ClhsdbJstack true
 */

/**
 * @test
 * @bug 8190198
 * @requires vm.compMode != "Xcomp"
 * @summary Test clhsdb Jstack command
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm/timeout=480 ClhsdbJstack false
 */

public class ClhsdbJstack {

    private static void testJstack(boolean withXcomp) throws Exception {
        LingeredApp theApp = null;
        try {
            ClhsdbLauncher test = new ClhsdbLauncher();
            theApp = withXcomp ? LingeredApp.startApp("-Xcomp")
                               : LingeredApp.startApp();
            System.out.print("Started LingeredApp ");
            if (withXcomp) {
                System.out.print("(-Xcomp) ");
            }
            System.out.println("with pid " + theApp.getPid());

            List<String> cmds = List.of("jstack -v");

            Map<String, List<String>> expStrMap = new HashMap<>();
            expStrMap.put("jstack -v", List.of(
                    "No deadlocks found",
                    "Common\\-Cleaner",
                    "Signal Dispatcher",
                    "java.lang.ref.Finalizer\\$FinalizerThread.run",
                    "java.lang.ref.Reference",
                    "Method\\*",
                    "LingeredApp.steadyState"));

            test.run(theApp.getPid(), cmds, expStrMap, null);
        } catch (SkippedException se) {
            throw se;
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR (with -Xcomp=" + withXcomp + ") " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
    }

    public static void main(String[] args) throws Exception {
        boolean xComp = Boolean.parseBoolean(args[0]);
        System.out.println("Starting ClhsdbJstack test");
        testJstack(xComp);
        System.out.println("Test PASSED");
    }
}
