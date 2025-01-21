/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8277100
 * @summary VM should exit with an error message if the specified dynamic archive
 *          is the same as the default CDS archive.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. DumpToDefaultArchive
 */

import jdk.test.lib.helpers.ClassFileInstaller;
import jdk.test.whitebox.WhiteBox;

public class DumpToDefaultArchive extends DynamicArchiveTestBase {

    public static void main(String[] args) throws Exception {
        runTest(DumpToDefaultArchive::doTest);
    }

    private static void doTest() throws Exception {
        WhiteBox wb = WhiteBox.getWhiteBox();
        String topArchiveName = wb.getDefaultArchivePath();

        dump(topArchiveName,
             "-Xlog:cds",
             "-version")
            .assertAbnormalExit(output -> {
                    output.shouldContain("Cannot specify the default CDS archive for -XX:ArchiveClassesAtExit: "
                        + topArchiveName);
                });
    }
}
