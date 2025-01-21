/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8247732
 * @summary Test ControlIntrinsic via jcmd
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver compiler.compilercontrol.jcmd.ControlIntrinsicTest
 */

package compiler.compilercontrol.jcmd;

import compiler.compilercontrol.share.IntrinsicCommand;
import compiler.compilercontrol.share.IntrinsicCommand.IntrinsicId;
import compiler.compilercontrol.share.scenario.Scenario;

public class ControlIntrinsicTest {
    public static void main(String[] args) {
        IntrinsicId ids[] = new IntrinsicId[3];

        ids[0] = new IntrinsicId("_newArray", true);
        ids[1] = new IntrinsicId("_minF", false);
        ids[2] = new IntrinsicId("_copyOf", true);
        new IntrinsicCommand(Scenario.Type.JCMD, ids, true).test();

        // invalid compileCommands, hotspot exits with non-zero retval
        ids[0] = new IntrinsicId("brokenIntrinsic", true);
        ids[1] = new IntrinsicId("invalidIntrinsic", false);
        new IntrinsicCommand(Scenario.Type.JCMD, ids, false).test();
    }
}
