/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test SharedStringsAuto
 * @summary Test -Xshare:auto with shared strings.
 * @requires vm.cds.write.archived.java.heap
 * @library /test/lib
 * @run driver SharedStringsRunAuto
 */

import jdk.test.lib.cds.CDSTestUtils;

public class SharedStringsRunAuto {
    public static void main(String[] args) throws Exception {
        CDSTestUtils.createArchiveAndCheck()
            .shouldContain( "Shared string table stats");
        CDSTestUtils.runWithArchiveAndCheck();
    }
}
