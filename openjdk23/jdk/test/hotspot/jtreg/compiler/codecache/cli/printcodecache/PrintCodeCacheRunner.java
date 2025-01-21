/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.codecache.cli.printcodecache;

import compiler.codecache.cli.common.CodeCacheCLITestCase;
import compiler.codecache.cli.common.CodeCacheInfoFormatter;
import compiler.codecache.cli.common.CodeCacheOptions;
import jdk.test.lib.process.ExitCode;
import jdk.test.lib.cli.CommandLineOptionTest;
import jdk.test.whitebox.code.BlobType;

import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * Runner implementation aimed to verify PrintCodeCache output.
 */
public class PrintCodeCacheRunner implements CodeCacheCLITestCase.Runner {
    private final boolean printCodeCache;

    public PrintCodeCacheRunner(boolean printCodeCache) {
        this.printCodeCache = printCodeCache;
    }

    public PrintCodeCacheRunner() {
        this(true);
    }

    @Override
    public void run(CodeCacheCLITestCase.Description testCaseDescription,
            CodeCacheOptions options) throws Throwable {
        CodeCacheOptions expectedValues
                = testCaseDescription.expectedValues(options);

        String[] expectedMessages
                = testCaseDescription.involvedCodeHeaps.stream()
                        .map(heap -> CodeCacheInfoFormatter.forHeap(heap)
                                .withSize(expectedValues.sizeForHeap(heap)))
                        .map(CodeCacheInfoFormatter::getInfoString)
                        .toArray(String[]::new);

        EnumSet<BlobType> unexpectedHeapsSet
                = EnumSet.complementOf(testCaseDescription.involvedCodeHeaps);

        String[] unexpectedMessages = CodeCacheInfoFormatter.forHeaps(
                unexpectedHeapsSet.toArray(
                        new BlobType[unexpectedHeapsSet.size()]));

        String description = String.format("JVM output should contain entries "
                + "for following code heaps: [%s] and should not contain "
                + "entries for following code heaps: [%s].",
                testCaseDescription.involvedCodeHeaps.stream()
                        .map(BlobType::name)
                        .collect(Collectors.joining(", ")),
                unexpectedHeapsSet.stream()
                        .map(BlobType::name)
                        .collect(Collectors.joining(", ")));

        CommandLineOptionTest.verifySameJVMStartup(expectedMessages,
                unexpectedMessages, "JVM startup failure is not expected, "
                + "since all options have allowed values", description,
                ExitCode.OK,
                testCaseDescription.getTestOptions(options,
                        CommandLineOptionTest.prepareBooleanFlag(
                                "PrintCodeCache", printCodeCache),
                        // Do not use large pages to avoid large page
                        // alignment of code heaps affecting their size.
                        "-XX:-UseLargePages"));
    }
}
