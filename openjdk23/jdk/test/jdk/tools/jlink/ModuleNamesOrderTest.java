/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.spi.ToolProvider;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tests.Helper;
import tests.JImageGenerator;
import tests.JImageGenerator.JLinkTask;

/*
 * @test
 * @bug 8168925
 * @summary MODULES property should be topologically ordered and space-separated list
 * @library ../lib
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jmod
 *          jdk.jlink/jdk.tools.jimage
 *          jdk.compiler
 *          jdk.jshell
 *
 * @build tests.*
 * @run main ModuleNamesOrderTest
 */
public class ModuleNamesOrderTest {
    static final ToolProvider JLINK_TOOL = ToolProvider.findFirst("jlink")
        .orElseThrow(() ->
            new RuntimeException("jlink tool not found")
        );

    public static void main(String[] args) throws Exception {
        Helper helper = Helper.newHelper();
        if (helper == null) {
            System.err.println("Test not run");
            return;
        }

        testDependences(helper);
        testModulesOrder(helper);
    }

    private static List<String> modulesProperty(Path outputDir, String modulePath, String... roots)
        throws IOException
    {
        JLinkTask jlinkTask = JImageGenerator.getJLinkTask()
                                             .modulePath(modulePath)
                                             .output(outputDir);
        Stream.of(roots).forEach(jlinkTask::addMods);
        jlinkTask.call().assertSuccess();

        File release = new File(outputDir.toString(), "release");
        if (!release.exists()) {
            throw new AssertionError("release not generated");
        }

        Properties props = new Properties();
        try (FileReader reader = new FileReader(release)) {
            props.load(reader);
        }

        String modules = props.getProperty("MODULES");
        if (!modules.startsWith("\"java.base ")) {
            throw new AssertionError("MODULES should start with 'java.base'");
        }
        if (modules.charAt(0) != '"' || modules.charAt(modules.length()-1) != '"') {
            throw new AssertionError("MODULES value should be double quoted");
        }

        return Stream.of(modules.substring(1, modules.length()-1).split("\\s+"))
                     .collect(Collectors.toList());
    }

    private static void testDependences(Helper helper) throws IOException {
        Path outputDir = helper.createNewImageDir("test");
        List<String> modules = modulesProperty(outputDir, helper.defaultModulePath(),
            "jdk.jshell");
        String last = modules.get(modules.size()-1);
        if (!last.equals("jdk.jshell")) {
            throw new AssertionError("Unexpected MODULES value: " + modules);
        }

        checkDependency(modules, "java.logging", "java.base");
        checkDependency(modules, "jdk.compiler", "java.compiler");
        checkDependency(modules, "jdk.jshell", "java.logging");
        checkDependency(modules, "jdk.jshell", "jdk.compiler");
    }

    /*
     * Verify the MODULES list must be the same for the same module graph
     */
    private static void testModulesOrder(Helper helper) throws IOException {
        Path image1 = helper.createNewImageDir("test1");
        List<String> modules1 = modulesProperty(image1, helper.defaultModulePath(),
            "jdk.jshell");
        Path image2 = helper.createNewImageDir("test2");
        List<String> modules2 = modulesProperty(image2, helper.defaultModulePath(),
            "jdk.jshell");
        if (!modules1.equals(modules2)) {
            throw new AssertionError("MODULES should be a stable order: " +
                modules1 + " vs " + modules2);
        }
    }

    private static void checkDependency(List<String> modules, String fromMod, String toMod) {
        int fromModIdx = modules.indexOf(fromMod);
        if (fromModIdx == -1) {
            throw new AssertionError(fromMod + " is missing in MODULES");
        }
        int toModIdx = modules.indexOf(toMod);
        if (toModIdx == -1) {
            throw new AssertionError(toMod + " is missing in MODULES");
        }

        if (toModIdx > fromModIdx) {
            throw new AssertionError("in MODULES, " + fromMod + " should appear after " + toMod);
        }
    }
}
