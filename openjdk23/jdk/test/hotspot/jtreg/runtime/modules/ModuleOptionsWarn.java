/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8162415
 * @summary Test warnings for ignored properties.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run driver ModuleOptionsWarn
 */

import java.util.Map;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

// Test that the VM behaves correctly when processing command line module system properties.
public class ModuleOptionsWarn {

    public static void main(String[] args) throws Exception {

        // Test that a warning is not issued for extraneous jdk.module properties.
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.ignored", "-version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.addmods", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property ending in '.'.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.limitmods.", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property ending in '='.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.addexports=", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property ending in ".stuff"
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.addreads.stuff", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property ending in "=stuff"
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.path=stuff", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property ending in ".="
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.upgrade.path.=xx", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for a reserved jdk.module property ending in ".<num>"
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.patch.3=xx", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning can be suppressed for module related properties that get ignored.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-Djdk.module.addmods", "-XX:-PrintWarnings", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is not issued for properties of the form "jdk.module.main"
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintWarnings", "-Djdk.module.main.ignored", "-version");
        output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("Ignoring system property option");
        output.shouldHaveExitValue(0);

        // Test that a warning is issued for module related properties specified using _JAVA_OPTIONS.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+PrintWarnings", "-version");
        Map<String, String> env = pb.environment();
        env.put("_JAVA_OPTIONS", "-Djdk.module.addreads");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Ignoring system property option");
        output.shouldHaveExitValue(0);
    }
}
