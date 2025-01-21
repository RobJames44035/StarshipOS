/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Basic plus test for shared strings
 * @requires vm.cds.write.archived.java.heap
 * @requires vm.gc == null
 * @library /test/hotspot/jtreg/runtime/cds/appcds /test/lib
 * @build HelloStringPlus jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver SharedStringsBasicPlus
 */

public class SharedStringsBasicPlus {
    public static void main(String[] args) throws Exception {
        SharedStringsUtils.run(args, SharedStringsBasicPlus::test);
    }

    public static void test(String[] args) throws Exception {
        SharedStringsUtils.buildJarAndWhiteBox("HelloStringPlus");

        SharedStringsUtils.dumpWithWhiteBox( TestCommon.list("HelloStringPlus"),
            "SharedStringsBasic.txt", "-Xlog:cds,cds+hashtables");

        SharedStringsUtils.runWithArchiveAndWhiteBox("HelloStringPlus");
    }
}
