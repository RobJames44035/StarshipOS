/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8054386
 * @summary java debugging test for CDS
 * @requires vm.cds
 * @modules jdk.jdi
 *          java.management
 *          jdk.jartool/sun.tools.jar
 * @library /test/lib
 * @library ..
 * @build TestScaffold VMConnection TargetListener TargetAdapter
 * @build CDSJDITest
 * @run compile -g ../FieldWatchpoints.java
 * @run main CDSFieldWatchpoints
 */

/*
 * Launch the JDI FieldWatchpoints test, which will setup field watchpoints in
 * FieldWatchpointsDebugee. FieldWatchpointsDebugee is first dumped into the
 * CDS archive, so this will test debugging a class in the archive.
 */

public class CDSFieldWatchpoints extends CDSJDITest {
    static String jarClasses[] = {
        // FieldWatchpointsDebugee. A, and B are the only classes we need in the archive.
        // FieldWatchpointsDebugee will be launched by FieldWatchpoints as the debuggee
        // application. Note, compiling FieldWatchpoints.java above will cause
        // FieldWatchpointsDebugee to be compiled since it is also in FieldWatchpoints.java.
        "FieldWatchpointsDebugee", "A", "B",
    };
    static String testname = "FieldWatchpoints";

    public static void main(String[] args) throws Exception {
        runTest(testname, jarClasses);
    }
}
