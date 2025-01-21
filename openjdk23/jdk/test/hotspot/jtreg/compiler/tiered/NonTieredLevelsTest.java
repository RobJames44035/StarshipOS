/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test NonTieredLevelsTest
 * @summary Verify that only one level can be used
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @requires vm.opt.TieredStopAtLevel==null
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=compileonly,compiler.whitebox.SimpleTestCaseHelper::*
 *                   compiler.tiered.NonTieredLevelsTest
 */

package compiler.tiered;

import java.util.function.IntPredicate;
import compiler.whitebox.CompilerWhiteBoxTest;
import jdk.test.lib.Platform;
import jtreg.SkippedException;

public class NonTieredLevelsTest extends CompLevelsTest {
    private static final int AVAILABLE_COMP_LEVEL;
    private static final IntPredicate IS_AVAILABLE_COMPLEVEL;
    static {
        if (Platform.isServer() && !Platform.isEmulatedClient()) {
            AVAILABLE_COMP_LEVEL = COMP_LEVEL_FULL_OPTIMIZATION;
            IS_AVAILABLE_COMPLEVEL = x -> x == COMP_LEVEL_FULL_OPTIMIZATION;
        } else if (Platform.isClient() || Platform.isMinimal() || Platform.isEmulatedClient()) {
            AVAILABLE_COMP_LEVEL = COMP_LEVEL_SIMPLE;
            IS_AVAILABLE_COMPLEVEL = x -> x == COMP_LEVEL_SIMPLE;
        } else {
            throw new Error("TESTBUG: unknown VM: " + Platform.vmName);
        }

    }
    public static void main(String[] args) throws Exception {
        if (CompilerWhiteBoxTest.skipOnTieredCompilation(true)) {
            throw new SkippedException("Test isn't applicable for tiered mode");
        }
        CompilerWhiteBoxTest.main(NonTieredLevelsTest::new, args);
    }

    private NonTieredLevelsTest(TestCase testCase) {
        super(testCase);
        // to prevent inlining of #method
        WHITE_BOX.testSetDontInlineMethod(method, true);
    }

    @Override
    protected void test() throws Exception {
        if (skipXcompOSR()) {
          return;
        }
        checkNotCompiled();
        compile();
        checkCompiled();

        int compLevel = getCompLevel();
        checkLevel(AVAILABLE_COMP_LEVEL, compLevel);
        int bci = WHITE_BOX.getMethodEntryBci(method);
        deoptimize();
        if (!testCase.isOsr()) {
            for (int level = 1; level <= COMP_LEVEL_MAX; ++level) {
                if (IS_AVAILABLE_COMPLEVEL.test(level)) {
                    testAvailableLevel(level, bci);
                } else {
                    testUnavailableLevel(level, bci);
                }
            }
        } else {
            System.out.println("skip other levels testing in OSR");
            testAvailableLevel(AVAILABLE_COMP_LEVEL, bci);
        }
    }
}

