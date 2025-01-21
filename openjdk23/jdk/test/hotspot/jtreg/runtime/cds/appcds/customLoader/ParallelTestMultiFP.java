/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Load classes from CDS archive into multiple custom loader using parallel threads
 * @requires vm.cds
 * @requires vm.cds.custom.loaders
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile ../test-classes/ParallelLoad.java ../test-classes/ParallelClasses.java
 * @run driver ParallelTestMultiFP
 */

public class ParallelTestMultiFP extends ParallelTestBase {
    public static void main(String[] args) throws Exception {
        ParallelTestBase.run(args, MULTI_CUSTOM_LOADER, FINGERPRINT_MODE);
    }
}
