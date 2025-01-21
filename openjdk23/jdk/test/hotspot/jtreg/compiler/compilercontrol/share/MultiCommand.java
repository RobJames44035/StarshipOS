/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share;

import compiler.compilercontrol.share.method.MethodDescriptor;
import compiler.compilercontrol.share.scenario.Command;
import compiler.compilercontrol.share.scenario.CommandGenerator;
import compiler.compilercontrol.share.scenario.CompileCommand;
import compiler.compilercontrol.share.scenario.Scenario;
import jdk.test.lib.Utils;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

import static compiler.compilercontrol.share.IntrinsicCommand.VALID_INTRINSIC_SAMPLES;
import static compiler.compilercontrol.share.IntrinsicCommand.INVALID_INTRINSIC_SAMPLES;

public class MultiCommand extends AbstractTestBase {
    private final List<CompileCommand> testCases;

    public MultiCommand(List<CompileCommand> testCases) {
        this.testCases = testCases;
    }

    /**
     * Generates a test containing multiple random commands
     *
     * @param validOnly shows that all commands should be valid
     * @return test instance to run
     */
    public static AbstractTestBase generateRandomTest(boolean validOnly) {
        CommandGenerator cmdGen = new CommandGenerator();
        List<Command> commands = cmdGen.generateCommands();
        List<CompileCommand> testCases = new ArrayList<>();

        for (Command cmd : commands) {
            boolean isValid = true;
            String argument = null;

            if (validOnly && cmd == Command.NONEXISTENT) {
                // replace with a valid command
                cmd = Command.EXCLUDE;
            }
            if (cmd == Command.INTRINSIC) {
                if (validOnly) {
                    argument = Utils.getRandomElement(VALID_INTRINSIC_SAMPLES);
                } else {
                    argument = Utils.getRandomElement(INVALID_INTRINSIC_SAMPLES);
                    isValid = false;
                }
            }

            Executable exec = Utils.getRandomElement(METHODS).first;
            MethodDescriptor md;

            // Command.quiet discards the method descriptor - can never fail on the method descriptor
            if (validOnly || cmd == Command.QUIET) {
                md = AbstractTestBase.getValidMethodDescriptor(exec);
            } else {
                md = AbstractTestBase.METHOD_GEN.generateRandomDescriptor(exec);
                isValid &= md.isValid();
            }
            CompileCommand cc;
            if (cmd == Command.INTRINSIC) {
                cc = cmdGen.generateCompileCommand(cmd, isValid, md, null, argument);
            } else {
                cc = cmdGen.generateCompileCommand(cmd, isValid, md, null);
            }
            testCases.add(cc);
        }
        return new MultiCommand(testCases);
    }

    @Override
    public void test() {
        Scenario.Builder builder = Scenario.getBuilder();
        builder.addFlag("-Xmixed");
        builder.addFlag("-XX:+UnlockDiagnosticVMOptions");
        builder.addFlag("-XX:CompilerDirectivesLimit=101");
        for (CompileCommand cc : testCases) {
            builder.add(cc);
        }
        Scenario scenario = builder.build();
        scenario.execute();
    }
}
