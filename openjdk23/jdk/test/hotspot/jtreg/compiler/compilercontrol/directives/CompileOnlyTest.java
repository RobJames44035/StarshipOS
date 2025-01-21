/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Tests directives to be able to compile only specified  methods
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver compiler.compilercontrol.directives.CompileOnlyTest
 */

package compiler.compilercontrol.directives;

import compiler.compilercontrol.share.SingleCommand;
import compiler.compilercontrol.share.scenario.Command;
import compiler.compilercontrol.share.scenario.Scenario;

public class CompileOnlyTest {
    public static void main(String[] args) {
        new SingleCommand(Command.COMPILEONLY, Scenario.Type.DIRECTIVE)
                .test();
    }
}
