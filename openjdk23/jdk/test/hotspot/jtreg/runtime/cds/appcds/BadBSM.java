/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary CDS dump should abort if a class file contains a bad BSM.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/WrongBSM.jcod
 * @run driver BadBSM
 */

import jdk.test.lib.process.OutputAnalyzer;

public class BadBSM {

  public static void main(String[] args) throws Exception {
    JarBuilder.build("wrongbsm", "WrongBSM");

    String appJar = TestCommon.getTestJar("wrongbsm.jar");

    OutputAnalyzer out = TestCommon.dump(appJar,
        TestCommon.list("WrongBSM",
                        "@lambda-proxy WrongBSM 7"),
        "-Xlog:cds+lambda=debug");
    out.shouldHaveExitValue(0)
       .shouldContain("resolve_indy for class WrongBSM has encountered exception: java.lang.NoSuchMethodError");
  }
}
