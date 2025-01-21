/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.codecache.cli.codeheapsize;

import compiler.codecache.cli.common.CodeCacheCLITestCase;
import compiler.codecache.cli.common.CodeCacheOptions;
import jdk.test.lib.cli.CommandLineOptionTest;
import jdk.test.whitebox.code.BlobType;

/**
 * Test case runner aimed to verify that all four options related to code cache
 * sizing have correct values.
 */
public class GenericCodeHeapSizeRunner implements CodeCacheCLITestCase.Runner {
    @Override
    public void run(CodeCacheCLITestCase.Description testCaseDescription,
            CodeCacheOptions options) throws Throwable {
        CodeCacheOptions expectedValues
                = options.mapOptions(testCaseDescription.involvedCodeHeaps);

        CommandLineOptionTest.verifyOptionValueForSameVM(
                BlobType.All.sizeOptionName,
                Long.toString(expectedValues.reserved),
                String.format("%s should have value %d.",
                        BlobType.All.sizeOptionName, expectedValues.reserved),
                testCaseDescription.getTestOptions(options));

        CommandLineOptionTest.verifyOptionValueForSameVM(
                BlobType.NonNMethod.sizeOptionName,
                Long.toString(expectedValues.nonNmethods),
                String.format("%s should have value %d.",
                        BlobType.NonNMethod.sizeOptionName,
                        expectedValues.nonNmethods),
                testCaseDescription.getTestOptions(options));

        CommandLineOptionTest.verifyOptionValueForSameVM(
                BlobType.MethodNonProfiled.sizeOptionName,
                Long.toString(expectedValues.nonProfiled),
                String.format("%s should have value %d.",
                        BlobType.MethodNonProfiled.sizeOptionName,
                        expectedValues.nonProfiled),
                testCaseDescription.getTestOptions(options));

        CommandLineOptionTest.verifyOptionValueForSameVM(
                BlobType.MethodProfiled.sizeOptionName,
                Long.toString(expectedValues.profiled),
                String.format("%s should have value %d.",
                        BlobType.MethodProfiled.sizeOptionName,
                        expectedValues.profiled),
                testCaseDescription.getTestOptions(options));
    }
}
