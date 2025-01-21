/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Tests jcmd to be able to add a directive to compile only specified methods
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 * @requires vm.flavor != "minimal" & !vm.graal.enabled
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver compiler.compilercontrol.jcmd.PrintDirectivesTest
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

import java.lang.reflect.Executable;

import static compiler.compilercontrol.share.IntrinsicCommand.VALID_INTRINSIC_SAMPLES;

public class PrintDirectivesTest extends AbstractTestBase {
    private static final int AMOUNT = Utils.getRandomInstance().nextInt(
            Integer.getInteger("compiler.compilercontrol.jcmd."
                    + "PrintDirectivesTest.amount", 20));
    private final CommandGenerator cmdGen = new CommandGenerator();

    public static void main(String[] args) {
        new PrintDirectivesTest().test();
    }

    @Override
    public void test() {
        Scenario.Builder builder = Scenario.getBuilder();
        // Add some commands with directives file
        for (int i = 0; i < AMOUNT; i++) {
            String argument = null;

            Executable exec = Utils.getRandomElement(METHODS).first;
            MethodDescriptor methodDescriptor = getValidMethodDescriptor(exec);
            Command command = cmdGen.generateCommand();
            if (command == Command.NONEXISTENT) {
                // skip invalid command
                command = Command.COMPILEONLY;
            }
            if (command == Command.INTRINSIC) {
                argument = Utils.getRandomElement(VALID_INTRINSIC_SAMPLES);
            }
            CompileCommand compileCommand = new CompileCommand(command, true,
                    methodDescriptor, cmdGen.generateCompiler(),
                    Scenario.Type.DIRECTIVE, argument);
            builder.add(compileCommand);
        }
        // print all directives
        builder.add(new JcmdCommand(Command.NONEXISTENT, true, null, null,
                Scenario.Type.JCMD, Scenario.JcmdType.PRINT));
        Scenario scenario = builder.build();
        scenario.execute();
    }
}
