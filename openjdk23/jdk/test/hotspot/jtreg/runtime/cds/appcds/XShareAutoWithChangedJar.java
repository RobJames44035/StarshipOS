/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test -Xshare:auto for AppCDS
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @run driver XShareAutoWithChangedJar
 */

import jdk.test.lib.process.OutputAnalyzer;

public class XShareAutoWithChangedJar {
  public static void main(String[] args) throws Exception {
    String appJar = JarBuilder.build("XShareAutoWithChangedJar", "Hello");

    // 1. dump
    OutputAnalyzer output = TestCommon.dump(appJar, TestCommon.list("Hello"));
    TestCommon.checkDump(output);

    // 2. change the jar
    JarBuilder.build("XShareAutoWithChangedJar", "Hello");

    // 3. exec
    output = TestCommon.execAuto("-cp", appJar, "Hello");
    output.shouldContain("Hello World");
  }
}
