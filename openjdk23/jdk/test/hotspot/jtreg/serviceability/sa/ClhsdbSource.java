/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.test.lib.apps.LingeredApp;
import jtreg.SkippedException;

/**
 * @test
 * @bug 8192823
 * @summary Test clhsdb source command
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm ClhsdbSource
 */

public class ClhsdbSource {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting ClhsdbSource test");

        LingeredApp theApp = null;
        try {
            ClhsdbLauncher test = new ClhsdbLauncher();
            theApp = LingeredApp.startApp();
            System.out.println("Started LingeredApp with pid " + theApp.getPid());

            Path file = Paths.get("clhsdb_cmd_file");
            Files.write(file, "jstack -v\nhelp".getBytes());
            List<String> cmds = List.of("source clhsdb_cmd_file");

            Map<String, List<String>> expStrMap = new HashMap<>();
            expStrMap.put("source clhsdb_cmd_file", List.of(
                    "No deadlocks found",
                    "Common\\-Cleaner",
                    "Signal Dispatcher",
                    "java.lang.ref.Finalizer\\$FinalizerThread.run",
                    "java.lang.ref.Reference",
                    "Method\\*",
                    "LingeredApp.steadyState",
                    "Available commands:",
                    "attach pid \\| exec core",
                    "intConstant \\[ name \\[ value \\] \\]",
                    "type \\[ type \\[ name super isOop isInteger isUnsigned size \\] \\]"));

            Map<String, List<String>> unExpStrMap = new HashMap<>();
            unExpStrMap.put("source clhsdb_cmd_file", List.of(
                        "No such file or directory"));

            test.run(theApp.getPid(), cmds, expStrMap, unExpStrMap);
            Files.delete(file);
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
