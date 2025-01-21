/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
import java.io.PrintWriter;
import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;


/*
 * @test
 * @bug 8075030
 * @summary JvmtiEnv::GetObjectSize reports incorrect java.lang.Class instance size
 * @requires vm.jvmti
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.instrument
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @build GetObjectSizeClassAgent
 * @run driver jdk.test.lib.helpers.ClassFileInstaller GetObjectSizeClassAgent
 * @run driver GetObjectSizeClass
 */
public class GetObjectSizeClass {
    public static void main(String[] args) throws Exception  {
        PrintWriter pw = new PrintWriter("MANIFEST.MF");
        pw.println("Premain-Class: GetObjectSizeClassAgent");
        pw.close();

        ProcessBuilder pb = new ProcessBuilder();
        pb.command(new String[] { JDKToolFinder.getJDKTool("jar"), "cmf", "MANIFEST.MF", "agent.jar", "GetObjectSizeClassAgent.class"});
        pb.start().waitFor();

        ProcessBuilder pt = ProcessTools.createTestJavaProcessBuilder("-javaagent:agent.jar",  "GetObjectSizeClassAgent");
        OutputAnalyzer output = new OutputAnalyzer(pt.start());
        output.shouldHaveExitValue(0);

        output.stdoutShouldContain("GetObjectSizeClass passed");
    }
}
