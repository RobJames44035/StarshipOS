/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Tests CompileCommand=exclude
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver compiler.compilercontrol.commandfile.ExcludeTest
 */

package compiler.compilercontrol.commandfile;

import compiler.compilercontrol.share.SingleCommand;
import compiler.compilercontrol.share.scenario.Command;
import compiler.compilercontrol.share.scenario.Scenario;

public class ExcludeTest {
    public static void main(String[] args) {
        new SingleCommand(Command.EXCLUDE, Scenario.Type.FILE).test();
    }
}
