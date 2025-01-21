/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8015774
 * @summary Verify that PrintCodeCache option print correct information.
 * @requires vm.flagless
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 *
 * @run driver/timeout=240 compiler.codecache.cli.printcodecache.TestPrintCodeCacheOption
 */

package compiler.codecache.cli.printcodecache;

import compiler.codecache.cli.common.CodeCacheCLITestBase;
import compiler.codecache.cli.common.CodeCacheCLITestCase;
import jdk.test.whitebox.code.BlobType;

import java.util.EnumSet;

public class TestPrintCodeCacheOption extends CodeCacheCLITestBase {
    private static final CodeCacheCLITestCase DISABLED_PRINT_CODE_CACHE
            = new CodeCacheCLITestCase(new CodeCacheCLITestCase.Description(
                            options -> true, EnumSet.noneOf(BlobType.class)),
                    new PrintCodeCacheRunner(false));

    private static final CodeCacheCLITestCase.Runner DEFAULT_RUNNER
            = new PrintCodeCacheRunner();

    private TestPrintCodeCacheOption() {
        super(CodeCacheCLITestBase.OPTIONS_SET,
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.INT_MODE.description,
                        DEFAULT_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.NON_SEGMENTED.description,
                        DEFAULT_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.NON_TIERED.description,
                        DEFAULT_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.TIERED_LEVEL_0.description,
                        DEFAULT_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.TIERED_LEVEL_1.description,
                        DEFAULT_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.TIERED_LEVEL_4.description,
                        DEFAULT_RUNNER),
                DISABLED_PRINT_CODE_CACHE);
    }

    public static void main(String args[]) throws Throwable {
        new TestPrintCodeCacheOption().runTestCases();
    }
}
