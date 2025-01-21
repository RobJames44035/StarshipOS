/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test ArchiveDoesNotExist
 * @summary Test how VM handles "file does not exist" situation while
 *          attempting to use CDS archive. JVM should exit gracefully
 *          when sharing mode is ON, and continue w/o sharing if sharing
 *          mode is AUTO.
 * @requires vm.cds
 * @library /test/lib
 * @run driver ArchiveDoesNotExist
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;
import java.io.File;

public class ArchiveDoesNotExist {
    public static void main(String[] args) throws Exception {
        String fileName = "ArchiveDoesNotExist.jsa";

        File cdsFile = new File(fileName);
        if (cdsFile.exists())
            throw new RuntimeException("Test error: cds file already exists");

        CDSOptions opts = (new CDSOptions()).setArchiveName(fileName);
        opts.addPrefix("-Xlog:cds");
        // -Xshare=on
        OutputAnalyzer out = CDSTestUtils.runWithArchive(opts);
        CDSTestUtils.checkMappingFailure(out);
        out.shouldContain("Specified shared archive not found")
            .shouldHaveExitValue(1);

        // -Xshare=auto
        opts.setXShareMode("auto");
        out = CDSTestUtils.runWithArchive(opts);
        CDSTestUtils.checkMappingFailure(out);
        out.shouldMatch("(java|openjdk) version")
            .shouldNotContain("sharing")
            .shouldHaveExitValue(0);
    }
}
