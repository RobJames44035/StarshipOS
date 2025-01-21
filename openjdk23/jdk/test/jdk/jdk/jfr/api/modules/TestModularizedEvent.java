/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.modules;

import java.nio.file.Paths;

import static jdk.test.lib.Asserts.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestModularizedEvent {

    private static final String TEST_SRC = System.getProperty("test.src");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src_mods");
    private static final Path MODS_DIR = Paths.get("mods");

    private static final String ANNO_MODULE = "test.jfr.annotation";
    private static final String SETTING_MODULE = "test.jfr.setting";
    private static final String EVENT_MODULE = "test.jfr.event";
    private static final String TEST_MODULE = "test.jfr.main";

    public static void main(String... args) throws Exception {
        compileModule(ANNO_MODULE);
        compileModule(SETTING_MODULE);
        compileModule(EVENT_MODULE, "--module-path", MODS_DIR.toString());
        compileModule(TEST_MODULE, "--module-path", MODS_DIR.toString());

        OutputAnalyzer oa = ProcessTools.executeTestJava("--module-path", "mods", "-m", "test.jfr.main/test.jfr.main.MainTest");
        oa.stdoutShouldContain("Test passed.");
    }

    private static void compileModule(String modDir, String... opts) throws Exception {
        boolean compiled = compile(SRC_DIR.resolve(modDir),
                MODS_DIR.resolve(modDir),
                opts);
        assertTrue(compiled, "module " + modDir + " did not compile");
    }

    private static boolean compile(Path source, Path destination, String... options)
            throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            // no compiler available
            throw new UnsupportedOperationException("Unable to get system java compiler. "
                    + "Perhaps, jdk.compiler module is not available.");
        }
        StandardJavaFileManager jfm = compiler.getStandardFileManager(null, null, null);

        List<Path> sources
                = Files.find(source, Integer.MAX_VALUE,
                        (file, attrs) -> (file.toString().endsWith(".java")))
                .collect(Collectors.toList());

        Files.createDirectories(destination);
        jfm.setLocation(StandardLocation.CLASS_PATH, Collections.emptyList());
        jfm.setLocationFromPaths(StandardLocation.CLASS_OUTPUT,
                Arrays.asList(destination));

        List<String> opts = Arrays.asList(options);
        JavaCompiler.CompilationTask task
                = compiler.getTask(null, jfm, null, opts, null,
                        jfm.getJavaFileObjectsFromPaths(sources));

        return task.call();
    }

}
