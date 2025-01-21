/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test SharedStringsDedup
 * @summary Test -Xshare:auto with shared strings and -XX:+UseStringDeduplication
 * @requires vm.cds.write.archived.java.heap
 * @library /test/lib
 * @run driver SharedStringsDedup
 */

import jdk.test.lib.cds.CDSTestUtils;

// The main purpose is to test the interaction between shared strings
// and -XX:+UseStringDeduplication. We run in -Xshare:auto mode so
// we don't need to worry about CDS archive mapping failure (which
// doesn't happen often so it won't impact coverage).
public class SharedStringsDedup {
    public static void main(String[] args) throws Exception {
        CDSTestUtils.createArchiveAndCheck()
            .shouldContain("Shared string table stats");
        CDSTestUtils.runWithArchiveAndCheck("-XX:+UseStringDeduplication");
    }
}
