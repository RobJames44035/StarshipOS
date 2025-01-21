/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.util.CoreUtils;
import jdk.test.lib.Platform;
import jtreg.SkippedException;

/**
 * @test id=process
 * @bug 8190198
 * @summary Test clhsdb pmap command on a live process
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm ClhsdbPmap false
 */

/**
 * @test id=core
 * @bug 8190198
 * @summary Test clhsdb pmap command on a core file
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm/timeout=480 ClhsdbPmap true
 */

public class ClhsdbPmap {

    public static void main(String[] args) throws Exception {
        boolean withCore = Boolean.parseBoolean(args[0]);
        System.out.println("Starting ClhsdbPmap test: withCore==" + withCore);

        LingeredApp theApp = null;
        String coreFileName = null;
        try {
            ClhsdbLauncher test = new ClhsdbLauncher();
            theApp = new LingeredApp();
            theApp.setForceCrash(withCore);
            LingeredApp.startApp(theApp, CoreUtils.getAlwaysPretouchArg(withCore));
            System.out.println("Started LingeredApp with pid " + theApp.getPid());

            if (withCore) {
                String crashOutput = theApp.getOutput().getStdout();
                coreFileName = CoreUtils.getCoreFileLocation(crashOutput, theApp.getPid());
            }

            List<String> cmds = List.of("pmap");

            Map<String, List<String>> expStrMap = new HashMap<>();
            if (!withCore && Platform.isOSX()) {
                expStrMap.put("pmap", List.of("Not available for Mac OS X processes"));
            } else {
                expStrMap.put("pmap", List.of("jvm", "java", "jli", "jimage"));
            }

            if (withCore) {
                test.runOnCore(coreFileName, cmds, expStrMap, null);
            } else {
                test.run(theApp.getPid(), cmds, expStrMap, null);
            }
        } catch (SkippedException se) {
            throw se;
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            if (!withCore) {
                LingeredApp.stopApp(theApp);
            }
        }
        System.out.println("Test PASSED");
    }
}
