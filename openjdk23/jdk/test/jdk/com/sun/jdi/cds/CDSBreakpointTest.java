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
 * @run compile -g ../BreakpointTest.java
 * @run main CDSBreakpointTest
 */

/*
 * Launch the JDI BreakpointTest, which will set a debugger breakpoint in
 * BreakpointTarg. BreakpointTarg is first dumped into the CDS archive,
 * so this will test debugging a class in the archive.
 */

public class CDSBreakpointTest extends CDSJDITest {
    static String jarClasses[] = {
        // BreakpointTarg is the only class we need in the archive. It will
        // be launched by BreakpointTest as the debuggee application. Note,
        // compiling BreakpointTest.java above will cause BreakpointTarg to
        // be compiled since it is also in BreakpointTest.java.
        "BreakpointTarg",
    };
    static String testname = "BreakpointTest";

    public static void main(String[] args) throws Exception {
        runTest(testname, jarClasses);
    }
}
