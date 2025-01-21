/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.jcmd;

import compiler.compilercontrol.parser.HugeDirectiveUtil;
import compiler.compilercontrol.share.AbstractTestBase;
import compiler.compilercontrol.share.method.MethodDescriptor;
import compiler.compilercontrol.share.pool.PoolHelper;
import compiler.compilercontrol.share.scenario.Executor;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.TimeLimitedRunner;
import jdk.test.lib.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class StressAddJcmdBase {
    private static final int DIRECTIVES_AMOUNT = Integer.getInteger(
            "compiler.compilercontrol.jcmd.StressAddJcmdBase.directivesAmount",
            200);
    private static final int TIMEOUT = Integer.getInteger(
            "compiler.compilercontrol.jcmd.StressAddJcmdBase.timeout",
            30);
    private static final List<MethodDescriptor> DESCRIPTORS = new PoolHelper()
            .getAllMethods().stream()
                    .map(pair -> AbstractTestBase
                            .getValidMethodDescriptor(pair.first))
                    .collect(Collectors.toList());
    private static final String DIRECTIVE_FILE = "directives.json";
    private static final List<String> VM_OPTIONS = new ArrayList<>();
    private static final Random RANDOM = Utils.getRandomInstance();

    static {
        VM_OPTIONS.add("-Xmixed");
        VM_OPTIONS.add("-XX:+UnlockDiagnosticVMOptions");
        VM_OPTIONS.add("-XX:+LogCompilation");
        VM_OPTIONS.add("-XX:CompilerDirectivesLimit=1001");
    }

    /**
     * Performs test
     */
    public void test() {
        HugeDirectiveUtil.createHugeFile(DESCRIPTORS, DIRECTIVE_FILE,
                DIRECTIVES_AMOUNT);
        Executor executor = new TimeLimitedExecutor();
        List<OutputAnalyzer> outputAnalyzers = executor.execute();
        outputAnalyzers.get(0).shouldHaveExitValue(0);
    }

    /**
     * Makes connection to the test VM and performs a diagnostic command
     *
     * @param pid a pid of the VM under test
     * @return true if the test should continue invocation of this method
     */
    protected abstract boolean makeConnection(int pid);

    /**
     * Finish test executions
     */
    protected void finish() { }

    protected String nextCommand() {
        int i = RANDOM.nextInt(JcmdCommand.values().length);
        JcmdCommand jcmdCommand = JcmdCommand.values()[i];
        switch (jcmdCommand) {
            case ADD:
                return jcmdCommand.command + " " + DIRECTIVE_FILE;
            case PRINT:
            case CLEAR:
            case REMOVE:
                return jcmdCommand.command;
            default:
                throw new Error("TESTBUG: incorrect command: " + jcmdCommand);
        }
    }

    private enum JcmdCommand {
        ADD("Compiler.directives_add"),
        PRINT("Compiler.directives_print"),
        CLEAR("Compiler.directives_clear"),
        REMOVE("Compiler.directives_remove");

        public final String command;

        JcmdCommand(String command) {
            this.command = command;
        }
    }

    private class TimeLimitedExecutor extends Executor {
        public TimeLimitedExecutor() {
            /* There are no need to check the state */
            super(VM_OPTIONS, null, null);
        }

        @Override
        protected OutputAnalyzer[] executeJCMD(int pid) {
            TimeLimitedRunner runner = new TimeLimitedRunner(
                    TimeUnit.SECONDS.toMillis(TIMEOUT),
                    Utils.TIMEOUT_FACTOR,
                    () -> makeConnection(pid));
            try {
                runner.call();
            } catch (Exception e) {
                throw new Error("Exception during the execution: " + e, e);
            }
            finish();
            return new OutputAnalyzer[0];
        }
    }
}
