/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Check most common errors in file format
 * @requires vm.cds.write.archived.java.heap
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @build HelloString
 * @run driver InvalidFileFormat
 */

import java.io.File;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

// Checking most common error use cases
// This file is not an exhastive test of various shared data file corruption
// Note on usability intent: the shared data file is created and handled by
// the previledge person in the server environment.
public class InvalidFileFormat {
    public static void main(String[] args) throws Exception {
        SharedStringsUtils.run(args, InvalidFileFormat::test);
    }

    public static void test(String[] args) throws Exception {
        SharedStringsUtils.buildJar("HelloString");

        test("NonExistentFile.txt", "Unable to get hashtable dump file size");
        test("InvalidHeader.txt", "wrong version of hashtable dump file");
        test("InvalidVersion.txt", "wrong version of hashtable dump file");
        test("CorruptDataLine.txt", "Unknown data type. Corrupted at line 2");
        test("InvalidSymbol.txt", "Unexpected character. Corrupted at line 2");
        test("InvalidSymbolFormat.txt", "Unrecognized format. Corrupted at line 9");
        test("OverflowPrefix.txt", "Num overflow. Corrupted at line 4");
        test("UnrecognizedPrefix.txt", "Unrecognized format. Corrupted at line 5");
        test("TruncatedString.txt", "Truncated. Corrupted at line 3");
        test("LengthOverflow.txt", "string length too large: 2147483647");
    }

    private static void
        test(String dataFileName, String expectedWarning) throws Exception {
        System.out.println("Filename for testcase: " + dataFileName);

        OutputAnalyzer out = SharedStringsUtils.dumpWithoutChecks(TestCommon.list("HelloString"),
                                 "invalidFormat" + File.separator + dataFileName);

        CDSTestUtils.checkMappingFailure(out);
        out.shouldContain(expectedWarning).shouldHaveExitValue(1);
    }

}
