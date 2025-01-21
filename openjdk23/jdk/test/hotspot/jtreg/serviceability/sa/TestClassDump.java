/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.SA.SATestUtils;
import jtreg.SkippedException;

/**
 * @test
 * @bug 8184982
 * @summary Test ClassDump tool
 * @requires vm.hasSA
 * @library /test/lib
 * @run driver TestClassDump
 */

public class TestClassDump {

    private static void dumpClass(long lingeredAppPid)
        throws IOException {

        ProcessBuilder pb;
        OutputAnalyzer output;

        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Dsun.jvm.hotspot.tools.jcore.outputDir=jtreg_classes",
                "-m", "jdk.hotspot.agent/sun.jvm.hotspot.tools.jcore.ClassDump", String.valueOf(lingeredAppPid));
        SATestUtils.addPrivilegesIfNeeded(pb);
        output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        if (!Files.isDirectory(Paths.get("jtreg_classes"))) {
            throw new RuntimeException("jtreg_classes directory not found");
        }
        if (Files.notExists(Paths.get("jtreg_classes", "java", "lang", "Integer.class"))) {
            throw new RuntimeException("jtreg_classes/java/lang/Integer.class not found");
        }
        if (Files.notExists(Paths.get("jtreg_classes", "jdk", "test", "lib", "apps", "LingeredApp.class"))) {
            throw new RuntimeException("jtreg_classes/jdk/test/lib/apps/LingeredApp.class not found");
        }
        if (Files.notExists(Paths.get("jtreg_classes", "sun", "net", "util", "URLUtil.class"))) {
            throw new RuntimeException("jtreg_classes/sun/net/util/URLUtil.class not found");
        }

        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Dsun.jvm.hotspot.tools.jcore.outputDir=jtreg_classes2",
                "-Dsun.jvm.hotspot.tools.jcore.PackageNameFilter.pkgList=jdk,sun",
                "-m", "jdk.hotspot.agent/sun.jvm.hotspot.tools.jcore.ClassDump", String.valueOf(lingeredAppPid));
        SATestUtils.addPrivilegesIfNeeded(pb);
        output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        if (Files.exists(Paths.get("jtreg_classes2", "java", "math", "BigInteger.class"))) {
            throw new RuntimeException("jtreg_classes2/java/math/BigInteger.class not expected");
        }
        if (Files.notExists(Paths.get("jtreg_classes2", "sun", "util", "calendar", "BaseCalendar.class"))) {
            throw new RuntimeException("jtreg_classes2/sun/util/calendar/BaseCalendar.class not found");
        }
        if (Files.notExists(Paths.get("jtreg_classes2", "jdk", "internal", "loader", "BootLoader.class"))) {
            throw new RuntimeException("jtreg_classes2/jdk/internal/loader/BootLoader.class not found");
        }
    }

    public static void main(String[] args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        if (SATestUtils.needsPrivileges()) {
            // This test will create files as root that cannot be easily deleted, so don't run.
            throw new SkippedException("Cannot run this test on OSX if adding privileges is required.");
        }
        LingeredApp theApp = null;
        try {
            theApp = LingeredApp.startApp();
            long pid = theApp.getPid();
            System.out.println("Started LingeredApp with pid " + pid);
            dumpClass(pid);
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
        System.out.println("Test PASSED");
    }
}
