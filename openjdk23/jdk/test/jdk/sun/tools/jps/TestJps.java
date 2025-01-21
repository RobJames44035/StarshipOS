/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @modules jdk.jartool/sun.tools.jar
 * @build jdk.test.lib.apps.LingeredApp
 * @run main/othervm/timeout=360 TestJps
 */

 /*
  * Notes:
  *   @modules tag is ignored in driver mode, so need main/othervm
  *
  *   LingeredApp is pre-built separately to have jdk.test.lib classes ready
  *   before the test is compiled (see JDK-8242282).
  *
  *   Launching the process with relative path to an app jar file is not tested
  *
  *   This test resides in default package, so correct appearance
  *   of the full package name actually is not tested.
  */

import jdk.test.lib.apps.LingeredApp;

public class TestJps {

    public static void testJps(boolean useJar) throws Throwable {
        LingeredAppForJps app = new LingeredAppForJps();
        if (useJar) {
            app.buildJar();
        }
        try {
            LingeredApp.startApp(app, JpsHelper.getVmArgs());
            JpsHelper.runJpsVariants(app.getPid(),
                    app.getProcessName(), app.getFullProcessName(), app.getLockFileName());
        } finally {
            LingeredApp.stopApp(app);
        }
    }

    public static void main(String[] args) throws Throwable {
        testJps(false);
        testJps(true);
    }
}
