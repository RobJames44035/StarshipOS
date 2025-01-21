/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @summary test archive lambda invoker species type in dynamic dump
 * @bug 8280767 8327499
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive
 * @compile CDSLambdaInvoker.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar cds-test.jar CDSLambdaInvoker
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. TestLambdaInvokers
 */

public class TestLambdaInvokers extends DynamicArchiveTestBase {
    private static final String mainClass = "CDSLambdaInvoker";
    private static final String jarFile   = "cds-test.jar";
    private static void doTest(String topArchiveName) throws Exception {
        dump(topArchiveName,
             "-Xlog:cds",
             "-Xlog:cds+class=debug",
             "-Xlog:cds+dynamic=debug",
             "-cp",
             jarFile,
             mainClass)
             .assertNormalExit(output -> {
                 // This should be not be generated from the dynamic dump, as it should be included
                 // by the base archive.
                 output.shouldNotMatch("cds,class.*=.*java.lang.invoke.BoundMethodHandle.Species_IL");

                 // This should be generated from the dynamic dump
                 output.shouldMatch("cds,class.*=.*java.lang.invoke.BoundMethodHandle.Species_JL");
             });
        run(topArchiveName,
             "-Xlog:cds",
             "-Xlog:cds+dynamic=debug",
             "-Xlog:class+load",
             "-Djava.lang.invoke.MethodHandle.TRACE_RESOLVE=true",
             "-cp",
             jarFile,
             mainClass)
             .assertNormalExit(output -> {
                 // java.lang.invoke.BoundMethodHandle$Species_IL is loaded from base archive
                 output.shouldContain("java.lang.invoke.BoundMethodHandle$Species_IL source: shared objects file");

                 // java.lang.invoke.BoundMethodHandle$Species_JL is generated from CDSLambdaInvoker and
                 // stored in the dynamic archive
                 output.shouldContain("java.lang.invoke.BoundMethodHandle$Species_JL source: shared objects file (top)");

                 // java.lang.invoke.Invokers$Holder has invoker(Object,Object,Object,int)Object available
                 // from the archives
                 output.shouldContain("[LF_RESOLVE] java.lang.invoke.Invokers$Holder invoker L3I_L (success)");
             });
    }

    static void testWithDefaultBase() throws Exception {
        String topArchiveName = getNewArchiveName("top");
        doTest(topArchiveName);
    }

    public static void main(String[] args) throws Exception {
        runTest(TestLambdaInvokers::testWithDefaultBase);
    }
}
