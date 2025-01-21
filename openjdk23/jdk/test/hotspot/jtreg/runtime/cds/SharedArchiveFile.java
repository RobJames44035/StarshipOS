/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 8014138
 * @summary Testing new -XX:SharedArchiveFile=<file-name> option
 * @requires vm.cds
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;


// NOTE: This test serves as a sanity test and also as an example for simple
// use of SharedArchiveFile argument. For this reason it DOES NOT use the utility
// methods to form command line to create/use shared archive.
public class SharedArchiveFile {
    public static void main(String[] args) throws Exception {
        CDSOptions opts = (new CDSOptions())
            .addPrefix("-Xlog:cds")
            .setArchiveName("./SharedArchiveFile.jsa");
        CDSTestUtils.createArchiveAndCheck(opts);

        opts = (new CDSOptions())
            .setArchiveName("./SharedArchiveFile.jsa");
        CDSTestUtils.run(opts)
                    .assertNormalExit();
    }
}
