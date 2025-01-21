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
 * @bug 8191658
 * @summary Test clhsdb attach, detach, reattach commands
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm ClhsdbAttach
 */

public class ClhsdbAttach {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting ClhsdbAttach test");

        LingeredApp theApp = null;
        try {
            ClhsdbLauncher test = new ClhsdbLauncher();
            theApp = LingeredApp.startApp();
            System.out.println("Started LingeredApp with pid " + theApp.getPid());
            String attach = "attach " + theApp.getPid();

            List<String> cmds = List.of(
                    "where",
                    attach,
                    "flags MaxJavaStackTraceDepth",
                    "detach",
                    "universe",
                    "reattach",
                    "longConstant markWord::locked_value");

            Map<String, List<String>> expStrMap = new HashMap<>();
            expStrMap.put("where", List.of(
                    "Command not valid until attached to a VM"));
            expStrMap.put("flags MaxJavaStackTraceDepth", List.of(
                    "MaxJavaStackTraceDepth = "));
            expStrMap.put("universe", List.of(
                    "Command not valid until attached to a VM"));
            expStrMap.put("longConstant markWord::locked_value", List.of(
                    "longConstant markWord::locked_value"));

            test.run(-1, cmds, expStrMap, null);
        } catch (SkippedException se) {
            throw se;
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
        System.out.println("Test PASSED");
    }
}
