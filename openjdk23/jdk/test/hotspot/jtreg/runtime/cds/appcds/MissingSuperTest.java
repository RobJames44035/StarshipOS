/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary When super class is missing during dumping, no crash should happen.
 *
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/MissingSuper.java
 * @run driver MissingSuperTest
 */

public class MissingSuperTest {

  public static void main(String[] args) throws Exception {
    // The classes "MissingSuperSup" and "MissingSuperIntf" are intentionally not
    // included into the jar to provoke the test condition
    JarBuilder.build("missing_super", "MissingSuper",
        "MissingSuperSub", "MissingSuperImpl");

    String appJar = TestCommon.getTestJar("missing_super.jar");
    TestCommon.test(appJar, TestCommon.list("MissingSuper",
        "MissingSuperSub",
        "MissingSuperImpl"),
        "MissingSuper");
  }
}
