/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package common;

import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.Platform;

/**
 * A tool, such as jstat, jmap, etc Specific tools are defined as subclasses
 * parameterized by their corresponding ToolResults subclasses
 */
public abstract class TmTool<T extends ToolResults> {

    private final Class<T> resultsClz;
    private final String cmdLine;

    public TmTool(Class<T> resultsClz, String toolName, String otherArgs) {
        this.resultsClz = resultsClz;
        this.cmdLine = adjustForTestJava(toolName) + " " + otherArgs;
    }

    /**
     * Runs the tool to completion and returns the results
     *
     * @return the tool results
     * @throws Exception if anything goes wrong
     */
    public T measure() throws Exception {
        ToolRunner runner = new ToolRunner(cmdLine);
        ToolResults rawResults = runner.runToCompletion();
        System.out.println("Process output: " + rawResults);
        return resultsClz.getDeclaredConstructor(ToolResults.class).newInstance(rawResults);
    }

    private String adjustForTestJava(String toolName) {
        // We need to make sure we are running the tol from the JDK under testing
        String jdkPath = System.getProperty("test.jdk");
        if (jdkPath == null || !Paths.get(jdkPath).toFile().exists()) {
            throw new RuntimeException("property test.jdk not not set");
        }
        Path toolPath = Paths.get("bin", toolName + (Platform.isWindows() ? ".exe" : ""));
        return Paths.get(jdkPath, toolPath.toString()).toString();
    }

}
