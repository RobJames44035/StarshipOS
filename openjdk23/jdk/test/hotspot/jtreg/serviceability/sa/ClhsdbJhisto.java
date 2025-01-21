/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.Utils;
import jtreg.SkippedException;

/**
 * @test
 * @bug 8191658
 * @summary Test clhsdb jhisto command
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm ClhsdbJhisto
 */

public class ClhsdbJhisto {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting ClhsdbJhisto test");

        LingeredAppWithInterface theApp = null;
        try {
            ClhsdbLauncher test = new ClhsdbLauncher();

            theApp = new LingeredAppWithInterface();
            LingeredApp.startApp(theApp);
            System.out.println("Started LingeredApp with pid " + theApp.getPid());

            List<String> cmds = List.of("jhisto");

            Map<String, List<String>> expStrMap = new HashMap<>();
            expStrMap.put("jhisto", List.of(
                    "java.lang.String",
                    "java.util.HashMap",
                    "java.lang.Class",
                    "java.nio.HeapByteBuffer",
                    "java.net.URI",
                    "LingeredAppWithInterface",
                    "ParselTongue"));

            test.run(theApp.getPid(), cmds, expStrMap, null);
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
