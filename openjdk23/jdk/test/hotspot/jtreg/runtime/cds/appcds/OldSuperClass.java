/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
/*
 * @test
 * @bug 8261090
 * @summary CDS support of old classes with major version < JDK_6 (50) for static archive.
 *          Test with old super class.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/OldSuper.jasm
 * @compile test-classes/ChildOldSuper.java
 * @compile test-classes/GChild.java
 * @compile test-classes/OldSuperApp.java
 * @run driver OldSuperClass
 */

import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class OldSuperClass {
    public static void main(String[] args) throws Exception {
        String mainClass = "OldSuperApp";
        String namePrefix = "oldsuperclass";
        String appClasses[] = TestCommon.list("OldSuper", "ChildOldSuper", "GChild", mainClass);
        JarBuilder.build(namePrefix, appClasses);
        String appJar = TestCommon.getTestJar(namePrefix + ".jar");

        boolean dynamicMode = CDSTestUtils.DYNAMIC_DUMP;

        // create archive with class list
        OutputAnalyzer output = TestCommon.dump(appJar, appClasses, "-Xlog:class+load,cds=debug,verification=trace");
        TestCommon.checkExecReturn(output, 0,
                                   dynamicMode ? true : false,
                                   "Skipping OldSuper: Old class has been linked",
                                   "Skipping ChildOldSuper: Old class has been linked",
                                   "Skipping GChild: Old class has been linked");

        // run with archive
        TestCommon.run(
            "-cp", appJar,
            "-Xlog:class+load,cds=debug,verification=trace",
            mainClass)
          .assertNormalExit(out -> {
              out.shouldContain("Verifying class OldSuper with old format")
                 .shouldContain("Verifying class ChildOldSuper with new format")
                 .shouldContain("Verifying class GChild with new format");
              if (!dynamicMode) {
                  out.shouldContain("OldSuper source: shared objects file")
                     .shouldContain("ChildOldSuper source: shared objects file")
                     .shouldContain("GChild source: shared objects file");
              } else {
                  // Old classes were already linked before dynamic dump happened,
                  // so they couldn't be archived.
                  out.shouldMatch(".class.load.*OldSuper source:.*oldsuperclass.jar")
                     .shouldMatch(".class.load.*ChildOldSuper source:.*oldsuperclass.jar")
                     .shouldMatch(".class.load.*GChild source:.*oldsuperclass.jar");
              }
          });
    }
}
