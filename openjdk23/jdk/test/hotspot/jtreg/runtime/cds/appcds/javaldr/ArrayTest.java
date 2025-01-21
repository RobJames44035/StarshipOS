/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @summary test the ability to archive array classes and load them from the archive
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver ArrayTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.test.lib.process.OutputAnalyzer;

public class ArrayTest {

    static String arrayClasses[] = {
        ArrayTestHelper.class.getName(),
        "[Ljava/lang/Comparable;",
        "[I",
        "[[[Ljava/lang/Object;",
        "[[B"
    };

    public static void main(String[] args) throws Exception {
        JarBuilder.build("arrayTestHelper", "ArrayTestHelper");

        String appJar = TestCommon.getTestJar("arrayTestHelper.jar");
        JarBuilder.build(true, "WhiteBox", "jdk/test/whitebox/WhiteBox");
        String whiteBoxJar = TestCommon.getTestJar("WhiteBox.jar");
        String bootClassPath = "-Xbootclasspath/a:" + whiteBoxJar;

        // create an archive containing array classes
        OutputAnalyzer output = TestCommon.dump(appJar, TestCommon.list(arrayClasses), bootClassPath);
        // we currently don't support array classes during CDS dump
        output.shouldContain("Preload Warning: Cannot find [Ljava/lang/Comparable;")
              .shouldContain("Preload Warning: Cannot find [I")
              .shouldContain("Preload Warning: Cannot find [[[Ljava/lang/Object;")
              .shouldContain("Preload Warning: Cannot find [[B");

        List<String> argsList = new ArrayList<String>();
        argsList.add("-XX:+UnlockDiagnosticVMOptions");
        argsList.add("-XX:+WhiteBoxAPI");
        argsList.add("-cp");
        argsList.add(appJar);
        argsList.add(bootClassPath);
        argsList.add(ArrayTestHelper.class.getName());
        // the following are input args to the ArrayTestHelper.
        // skip checking array classes during run time
        for (int i = 0; i < 1; i++) {
            argsList.add(arrayClasses[i]);
        }
        String[] opts = new String[argsList.size()];
        opts = argsList.toArray(opts);
        TestCommon.run(opts).assertNormalExit();
    }
}
