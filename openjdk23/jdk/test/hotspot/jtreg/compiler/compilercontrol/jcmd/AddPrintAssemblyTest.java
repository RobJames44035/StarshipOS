/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Tests jcmd to be able to add a directive to print assembly
 *          only for specified methods
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver compiler.compilercontrol.jcmd.AddPrintAssemblyTest
 */

package compiler.compilercontrol.jcmd;

import compiler.compilercontrol.share.SingleCommand;
import compiler.compilercontrol.share.scenario.Command;
import compiler.compilercontrol.share.scenario.Scenario;

public class AddPrintAssemblyTest {
    public static void main(String[] args) {
        new SingleCommand(Command.PRINT, Scenario.Type.JCMD)
                .test();
    }
}
