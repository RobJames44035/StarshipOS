/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Exercise GC with shared strings
 * @requires vm.cds.write.archived.java.heap
 * @requires vm.gc == null
 * @library /test/hotspot/jtreg/runtime/cds/appcds /test/lib
 * @build HelloStringGC jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver ExerciseGC
 */
public class ExerciseGC {
    public static void main(String[] args) throws Exception {
        SharedStringsUtils.run(args, ExerciseGC::test);
    }
    public static void test(String[] args) throws Exception {
        SharedStringsUtils.buildJarAndWhiteBox("HelloStringGC");

        SharedStringsUtils.dumpWithWhiteBox(TestCommon.list("HelloStringGC"),
            "SharedStringsBasic.txt", "-Xlog:cds,cds+hashtables");

        SharedStringsUtils.runWithArchiveAndWhiteBox("HelloStringGC",
            "-XX:+UnlockDiagnosticVMOptions", "-XX:+VerifyBeforeGC");
    }
}
