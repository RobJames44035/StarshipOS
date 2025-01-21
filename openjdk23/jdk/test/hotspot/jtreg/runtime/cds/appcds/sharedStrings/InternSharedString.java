/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test shared strings together with string intern operation
 * @requires vm.cds.write.archived.java.heap
 * @requires vm.gc == null
 * @comment CDS archive heap mapping is not supported with large pages
 * @requires vm.opt.UseLargePages == null | !vm.opt.UseLargePages
 * @library /test/hotspot/jtreg/runtime/cds/appcds /test/lib
 * @compile InternStringTest.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver InternSharedString
 */

public class InternSharedString {
    public static void main(String[] args) throws Exception {
        SharedStringsUtils.run(args, InternSharedString::test);
    }

    public static void test(String[] args) throws Exception {
        SharedStringsUtils.buildJarAndWhiteBox("InternStringTest");

        SharedStringsUtils.dumpWithWhiteBox(TestCommon.list("InternStringTest"),
            "ExtraSharedInput.txt", "-Xlog:cds,cds+hashtables");

        String[] extraMatches = new String[]   {
            InternStringTest.passed_output1,
            InternStringTest.passed_output2,
            InternStringTest.passed_output3  };

        SharedStringsUtils.runWithArchiveAndWhiteBox(extraMatches, "InternStringTest");
    }
}
