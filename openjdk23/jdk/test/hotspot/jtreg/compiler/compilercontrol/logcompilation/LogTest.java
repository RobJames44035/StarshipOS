/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8137167
 * @summary Tests LogCompilation executed standalone without log commands or directives
 *
 * @requires !vm.graal.enabled
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver compiler.compilercontrol.logcompilation.LogTest
 */

package compiler.compilercontrol.logcompilation;

import compiler.compilercontrol.share.processors.LogProcessor;
import compiler.compilercontrol.share.scenario.Scenario;

public class LogTest {
    public static void main(String[] args) {
        Scenario.Builder builder = Scenario.getBuilder();
        builder.addFlag("-XX:+UnlockDiagnosticVMOptions");
        builder.addFlag("-Xbootclasspath/a:.");
        builder.addFlag("-XX:+WhiteBoxAPI");
        builder.addFlag("-XX:+LogCompilation");
        builder.addFlag("-XX:LogFile=" + LogProcessor.LOG_FILE);
        Scenario scenario = builder.build();
        scenario.execute();
    }
}
