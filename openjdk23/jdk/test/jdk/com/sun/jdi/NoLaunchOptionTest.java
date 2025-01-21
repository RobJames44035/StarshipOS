/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import static jdk.test.lib.Asserts.assertFalse;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test       NoLaunchOptionTest.java
 * @bug        4554734 4724714
 * @summary    Test for -Xrunjdwp:[onthrow,onuncaught] suboptions require launch suboption
 * @author     Tim Bell
 *
 * @library /test/lib
 *
 * @run compile -g NoLaunchOptionTest.java
 * @build VMConnection
 * @run driver NoLaunchOptionTest
 */
public class NoLaunchOptionTest extends Object {

    public static void main(String[] args) throws Exception {
        String[] cmd = VMConnection.insertDebuggeeVMOptions(new String[] {
                "-agentlib:jdwp=transport=dt_socket,address=5555," +
                "onthrow=java.lang.ClassNotFoundException,suspend=n",
                "NotAClass" });

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(cmd);
        OutputAnalyzer output = ProcessTools.executeProcess(pb);
        System.out.println(output.getOutput());

        assertFalse(output.getExitValue() == 0, "Exit code should not be 0");
        output.shouldContain("ERROR: JDWP Specify launch=<command line> when using onthrow or onuncaught suboption");
    }

}
