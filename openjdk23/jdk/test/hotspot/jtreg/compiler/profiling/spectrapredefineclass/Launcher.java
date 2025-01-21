/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8038636
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.instrument
 * @requires vm.jvmti & (vm.opt.TieredStopAtLevel == null | vm.opt.TieredStopAtLevel == 4)
 * @requires vm.compMode != "Xcomp"
 * @build compiler.profiling.spectrapredefineclass.Agent
 * @run driver jdk.test.lib.helpers.ClassFileInstaller compiler.profiling.spectrapredefineclass.Agent
 * @run driver compiler.profiling.spectrapredefineclass.Launcher
 * @run main/othervm -XX:CompilationMode=high-only -XX:-BackgroundCompilation -XX:CompileThreshold=10000
 *                   -XX:-UseOnStackReplacement -XX:TypeProfileLevel=222
 *                   -XX:ReservedCodeCacheSize=3M -Djdk.attach.allowAttachSelf
 *                   compiler.profiling.spectrapredefineclass.Agent
 */

package compiler.profiling.spectrapredefineclass;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Launcher {
    private static final String MANIFEST = "MANIFEST.MF";
    public static void main(String[] args) throws Exception  {
        try (PrintWriter pw = new PrintWriter(MANIFEST)) {
            pw.println("Agent-Class: " + Agent.class.getName());
            pw.println("Can-Retransform-Classes: true");
        }

        JDKToolLauncher jar = JDKToolLauncher.create("jar")
                .addToolArg("cmf")
                .addToolArg(MANIFEST)
                .addToolArg(Agent.AGENT_JAR)
                .addToolArg(Agent.class.getName().replace('.', File.separatorChar) + ".class");

        try {
            OutputAnalyzer output = ProcessTools.executeProcess(jar.getCommand());
            output.shouldHaveExitValue(0);
        } catch (Exception ex) {
            throw new Error("TESTBUG: jar failed.", ex);
        }
    }
}
