/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.TestRunner;
import toolbox.ToolBox;

/**
 * Base class for module tests.
 */
public class ModuleTestBase extends TestRunner {
    protected ToolBox tb;

    ModuleTestBase() {
        super(System.err);
        tb = new ToolBox();
    }

    /**
     * Run all methods annotated with @Test, and throw an exception if any
     * errors are reported..
     *
     * @throws Exception if any errors occurred
     */
    protected void runTests() throws Exception {
        runTests(m -> new Object[] { Paths.get(m.getName()) });
    }

    Path[] findJavaFiles(Path... paths) throws IOException {
        return tb.findJavaFiles(paths);
    }

    void checkOutputContains(String log, String... expect) throws Exception {
        for (String e : expect) {
            if (!log.contains(e)) {
                throw new Exception("expected output not found: " + e);
            }
        }
    }
}
