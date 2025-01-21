/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @key randomness
 * @bug 8015774
 * @summary Verify processing of options related to code heaps sizing.
 * @requires vm.flagless
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 *
 * @run driver/timeout=240 compiler.codecache.cli.codeheapsize.TestCodeHeapSizeOptions
 */

package compiler.codecache.cli.codeheapsize;

import compiler.codecache.cli.common.CodeCacheCLITestBase;
import compiler.codecache.cli.common.CodeCacheCLITestCase;
import jdk.test.lib.Platform;
import jdk.test.whitebox.code.BlobType;

import java.util.EnumSet;

public class TestCodeHeapSizeOptions extends CodeCacheCLITestBase {
    private static final CodeCacheCLITestCase JVM_STARTUP
            = new CodeCacheCLITestCase(new CodeCacheCLITestCase.Description(
                            options -> options.segmented,
                            EnumSet.noneOf(BlobType.class)),
                    new JVMStartupRunner());

    private static final CodeCacheCLITestCase CODE_CACHE_FREE_SPACE
            = new CodeCacheCLITestCase(new CodeCacheCLITestCase.Description(
                            options -> options.segmented
                                    && Platform.isDebugBuild(),
                            EnumSet.noneOf(BlobType.class)),
                    new CodeCacheFreeSpaceRunner());

    private static final GenericCodeHeapSizeRunner GENERIC_RUNNER
            = new GenericCodeHeapSizeRunner();

    private TestCodeHeapSizeOptions() {
        super(CodeCacheCLITestBase.OPTIONS_SET,
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.NON_TIERED.description,
                        GENERIC_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.TIERED_LEVEL_1.description,
                        GENERIC_RUNNER),
                new CodeCacheCLITestCase(CodeCacheCLITestCase
                        .CommonDescriptions.TIERED_LEVEL_4.description,
                        GENERIC_RUNNER),
                JVM_STARTUP,
                CODE_CACHE_FREE_SPACE);
    }

    public static void main(String args[]) throws Throwable {
        new TestCodeHeapSizeOptions().runTestCases();
    }
}
