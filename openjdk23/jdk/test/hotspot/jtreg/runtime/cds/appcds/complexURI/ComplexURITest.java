/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Verifies that CDS works with jar located in directories
 *          with names that need escaping
 * @bug 8339460
 * @requires vm.cds
 * @requires vm.cds.custom.loaders
 * @requires vm.flagless
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile mypackage/Main.java mypackage/Another.java
 * @run main/othervm ComplexURITest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Platform;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ComplexURITest {
    final static String moduleName = "mymodule";

    public static void main(String[] args) throws Exception {
        System.setProperty("test.noclasspath", "true");
        String jarFile = JarBuilder.build(moduleName, "mypackage/Main", "mypackage/Another");

        Path subDir = Path.of(".", "dir with space");
        Files.createDirectory(subDir);
        Path newJarFilePath = subDir.resolve(moduleName + ".jar");
        Files.move(Path.of(jarFile), newJarFilePath);
        jarFile = newJarFilePath.toString();

        final String listFileName = "test-classlist.txt";
        final String staticArchiveName = "test-static.jsa";
        final String dynamicArchiveName = "test-dynamic.jsa";

        // Verify static archive creation and use
        File fileList = new File(listFileName);
        delete(fileList.toPath());
        File staticArchive = new File(staticArchiveName);
        delete(staticArchive.toPath());

        createClassList(jarFile, listFileName);
        if (!fileList.exists()) {
            throw new RuntimeException("No class list created at " + fileList);
        }

        createArchive(jarFile, listFileName, staticArchiveName);
        if (!staticArchive.exists()) {
            throw new RuntimeException("No shared classes archive created at " + staticArchive);
        }

        useArchive(jarFile, staticArchiveName);

        // Verify dynamic archive creation and use
        File dynamicArchive = new File(dynamicArchiveName);
        delete(dynamicArchive.toPath());

        createDynamicArchive(jarFile, dynamicArchiveName);
        if (!dynamicArchive.exists()) {
            throw new RuntimeException("No dynamic archive created at " + dynamicArchive);
        }

        testDynamicArchive(jarFile, dynamicArchiveName);
    }

    private static void delete(Path path) throws Exception {
        if (Files.exists(path)) {
            if (Platform.isWindows()) {
                Files.setAttribute(path, "dos:readonly", false);
            }
            Files.delete(path);
        }
    }

    private static void createClassList(String jarFile, String list) throws Exception {
        String[] launchArgs  = {
                "-XX:DumpLoadedClassList=" + list,
                "--module-path",
                jarFile,
                "--module",
                moduleName + "/mypackage.Main"};
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(launchArgs);
        OutputAnalyzer output = TestCommon.executeAndLog(pb, "create-list");
        output.shouldHaveExitValue(0);
    }

    private static void createArchive(String jarFile, String list, String archive) throws Exception {
        String[] launchArgs  = {
                "-Xshare:dump",
                "-XX:SharedClassListFile=" + list,
                "-XX:SharedArchiveFile=" + archive,
                "--module-path",
                jarFile,
                "--module",
                moduleName + "/mypackage.Main"};
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(launchArgs);
        OutputAnalyzer output = TestCommon.executeAndLog(pb, "dump-archive");
        output.shouldHaveExitValue(0);
    }

    private static void useArchive(String jarFile, String archive) throws Exception {
        String[] launchArgs  = {
                "-Xshare:on",
                "-XX:SharedArchiveFile=" + archive,
                "--module-path",
                jarFile,
                "--module",
                moduleName + "/mypackage.Main"};
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(launchArgs);
        OutputAnalyzer output = TestCommon.executeAndLog(pb, "use-archive");
        output.shouldHaveExitValue(0);
    }

    private static void createDynamicArchive(String jarFile, String archive) throws Exception {
        String[] launchArgs  = {
                "-XX:ArchiveClassesAtExit=" + archive,
                "--module-path",
                jarFile,
                "--module",
                moduleName + "/mypackage.Main"};
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(launchArgs);
        OutputAnalyzer output = TestCommon.executeAndLog(pb, "dynamic-archive");
        output.shouldHaveExitValue(0);
    }

    private static void testDynamicArchive(String jarFile, String archive) throws Exception {
        String[] launchArgs  = {
                "-XX:SharedArchiveFile=" + archive,
                "-XX:+PrintSharedArchiveAndExit",
                "--module-path",
                jarFile,
                "--module",
                moduleName + "/mypackage.Main"};
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(launchArgs);
        OutputAnalyzer output = TestCommon.executeAndLog(pb, "dynamic-archive");
        output.shouldHaveExitValue(0);
        output.shouldContain("archive is valid");
        output.shouldContain(": mypackage.Main app_loader");
        output.shouldContain(": mypackage.Another unregistered_loader");
    }
}
