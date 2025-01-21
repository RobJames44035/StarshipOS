/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary When HelloA and HelloB are copied into the dynamic archive, the Symbols
 *          for their method's names will have a different sorting order. This requires
 *          that the dumped InstanceKlass to re-sort their "methods" array and re-layout the vtables/itables.
 *          A regression test for an earlier bug in DynamicArchiveBuilder::relocate_buffer_to_target().
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build MethodSortingApp
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar method_sorting.jar
 *             MethodSortingApp
 *             MethodSortingApp$HelloA
 *             MethodSortingApp$HelloA1
 *             MethodSortingApp$HelloB
 *             MethodSortingApp$HelloB1
 *             MethodSortingApp$InterfaceA
 *             MethodSortingApp$InterfaceB
 *             MethodSortingApp$ImplementorA
 *             MethodSortingApp$ImplementorA1
 *             MethodSortingApp$ImplementorB
 *             MethodSortingApp$ImplementorB1
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. MethodSorting
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class MethodSorting extends DynamicArchiveTestBase {
    public static void main(String[] args) throws Exception {
        runTest(MethodSorting::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("method_sorting.jar");
        String mainClass = "MethodSortingApp";

        dumpAndRun(topArchiveName, "-Xlog:cds+dynamic=debug", "-cp", appJar, mainClass);
    }
}
