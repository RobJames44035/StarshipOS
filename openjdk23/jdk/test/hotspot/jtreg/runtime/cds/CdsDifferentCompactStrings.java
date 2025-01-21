/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test CdsDifferentCompactStrings
 * @summary CDS (class data sharing) requires the same -XX:[+-]CompactStrings
 *          setting between archive creation time and load time.
 * @requires vm.cds
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 */

import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class CdsDifferentCompactStrings {
    public static void main(String[] args) throws Exception {
        createAndLoadSharedArchive("+", "-");
        createAndLoadSharedArchive("-", "+");
    }

    private static void createAndLoadSharedArchive(String create, String load)
        throws Exception
    {
        String createCompactStringsArgument = "-XX:" + create + "CompactStrings";
        String loadCompactStringsArgument   = "-XX:" + load   + "CompactStrings";

        CDSTestUtils.createArchiveAndCheck(createCompactStringsArgument);

        OutputAnalyzer out = CDSTestUtils.runWithArchive("-Xlog:cds", loadCompactStringsArgument);
        CDSTestUtils.checkMappingFailure(out);

        out.shouldMatch("The shared archive file's CompactStrings " +
                        "setting .* does not equal the current CompactStrings setting")
            .shouldHaveExitValue(1);
    }
}
