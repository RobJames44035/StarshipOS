/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */


/*
 * @test
 * @bug 8275775
 * @summary Test jcmd VM.classes
 * @library /test/lib
 * @run main/othervm PrintClasses
 */

/*
 * @test
 * @bug 8298162
 * @summary Test jcmd VM.classes with JFR
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm -XX:StartFlightRecording PrintClasses
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

public class PrintClasses {
  public static void main(String args[]) throws Exception {
    var pid = Long.toString(ProcessHandle.current().pid());
    var pb = new ProcessBuilder();

    pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.classes"});
    var output = new OutputAnalyzer(pb.start());
    output.shouldNotContain("instance size");
    output.shouldContain(PrintClasses.class.getSimpleName());

    pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.classes", "-verbose"});
    output = new OutputAnalyzer(pb.start());
    output.shouldContain("instance size");
    output.shouldContain(PrintClasses.class.getSimpleName());

    // Test for previous bug in misc flags printing
    output.shouldNotContain("##name");
  }
}
