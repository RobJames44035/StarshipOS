/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Tests jcmd to be able to clear directives added via options
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 *
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.compilercontrol.jcmd.ClearDirectivesFileStackTest
 */

package compiler.compilercontrol.jcmd;

import compiler.compilercontrol.share.AbstractTestBase;
import compiler.compilercontrol.share.method.MethodDescriptor;
import compiler.compilercontrol.share.scenario.Command;
import compiler.compilercontrol.share.scenario.CommandGenerator;
import compiler.compilercontrol.share.scenario.CompileCommand;
import compiler.compilercontrol.share.scenario.JcmdCommand;
import compiler.compilercontrol.share.scenario.Scenario;
import jdk.test.lib.Utils;
import jdk.test.whitebox.WhiteBox;

import java.lang.reflect.Executable;

public class ClearDirectivesFileStackTest extends AbstractTestBase {
    private static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();
    private static final int LIMIT = WHITE_BOX.getIntVMFlag("CompilerDirectivesLimit").intValue();
    private static final int AMOUNT = Utils.getRandomInstance().nextInt(LIMIT);
    private final CommandGenerator cmdGen = new CommandGenerator();

    public static void main(String[] args) {
        new ClearDirectivesFileStackTest().test();
    }

    @Override
    public void test() {
        Scenario.Builder builder = Scenario.getBuilder();
        // Add some commands with directives file
        for (int i = 0; i < AMOUNT; i++) {
            Executable exec = Utils.getRandomElement(METHODS).first;
            MethodDescriptor methodDescriptor = getValidMethodDescriptor(exec);
            Command command = cmdGen.generateCommand();
            if (command == Command.NONEXISTENT) {
                // skip invalid command
                command = Command.COMPILEONLY;
            }
            CompileCommand compileCommand = new CompileCommand(command,
                    methodDescriptor, cmdGen.generateCompiler(),
                    Scenario.Type.DIRECTIVE);
            builder.add(compileCommand);
        }
        // clear the stack
        builder.add(new JcmdCommand(Command.NONEXISTENT, null, null,
                Scenario.Type.JCMD, Scenario.JcmdType.CLEAR));
        // print all directives after the clear
        builder.add(new JcmdCommand(Command.NONEXISTENT, null, null,
                Scenario.Type.JCMD, Scenario.JcmdType.PRINT));
        Scenario scenario = builder.build();
        scenario.execute();
    }
}
