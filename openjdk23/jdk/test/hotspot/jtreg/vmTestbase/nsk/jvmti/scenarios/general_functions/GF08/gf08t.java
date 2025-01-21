/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package nsk.jvmti.scenarios.general_functions.GF08;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.util.Arrays;
import java.util.stream.Collectors;

public class gf08t {
    public static void main(String[] args) throws Exception {
        String libName = args[0];
        String className = args[1];
        String verboseType = args[2];
        String phrase = Arrays.stream(args)
                             .skip(3)
                             .collect(Collectors.joining(" "));

        OutputAnalyzer oa = ProcessTools.executeTestJava(
                "-agentlib:" + libName + "=-waittime=5 setVerboseMode=yes",
                className);
        oa.shouldHaveExitValue(95);
        oa.stdoutShouldContain(phrase);

        oa = ProcessTools.executeTestJava(
                "-agentlib:" + libName + "=-waittime=5 setVerboseMode=no",
                "-verbose:" + verboseType,
                className);
        oa.shouldHaveExitValue(95);
        oa.stdoutShouldContain(phrase);
    }
}
