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
import java.util.Arrays;
import java.util.stream.Collectors;

public class IntrinsicCommand extends AbstractTestBase {
    public static String[] VALID_INTRINSIC_SAMPLES = {"+_fabs", "-_maxF", "+_newArray", "-_isDigit", "+_putInt"};
    public static String[] INVALID_INTRINSIC_SAMPLES = {"+fabs", "-maxF"};

    public static class IntrinsicId {
        private String id;
        private boolean enable;

        public IntrinsicId(String id, boolean enable) {
            this.id = id;
            this.enable = enable;
        }

        @Override
        public String toString() {
            return (enable ? "+" : "-")  + id;
        }
    }

    private final Command command;
    private final Scenario.Type type;
    private final boolean isValid;
    private String intrinsic_ids;

    public IntrinsicCommand(Scenario.Type type, IntrinsicId[] intrinsic_ids, boolean isValid) {
        this.command = Command.INTRINSIC;
        this.type = type;
        this.intrinsic_ids = Arrays.stream(intrinsic_ids).map(id -> id.toString())
                                                         .collect(Collectors.joining(","));
        this.isValid = isValid;
    }

    @Override
    public void test() {
        Scenario.Builder builder = Scenario.getBuilder();
        Executable exec = Utils.getRandomElement(METHODS).first;
        MethodDescriptor md = getValidMethodDescriptor(exec);
        CommandGenerator cmdGen = new CommandGenerator();

        CompileCommand compileCommand = cmdGen.generateCompileCommand(command, isValid,
                md, type, intrinsic_ids);

        builder.add(compileCommand);
        Scenario scenario = builder.build();
        scenario.execute();
    }
}
