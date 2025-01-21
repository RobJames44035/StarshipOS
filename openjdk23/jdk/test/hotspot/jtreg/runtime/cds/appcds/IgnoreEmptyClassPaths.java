/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary Test the -XX:+IgnoreEmptyClassPaths flag
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @compile test-classes/HelloMore.java
 * @run driver IgnoreEmptyClassPaths
 */

import java.io.File;
import jdk.test.lib.process.OutputAnalyzer;

public class IgnoreEmptyClassPaths {

  public static void main(String[] args) throws Exception {
    String jar1 = JarBuilder.getOrCreateHelloJar();
    String jar2 = JarBuilder.build("IgnoreEmptyClassPaths_more", "HelloMore");

    String sep = File.pathSeparator;
    String cp_dump = jar1 + sep + jar2 + sep;
    String cp_exec = sep + jar1 + sep + sep + jar2 + sep;

    TestCommon.testDump(cp_dump, TestCommon.list("Hello", "HelloMore"),
                        "-Xlog:class+path=info", "-XX:+IgnoreEmptyClassPaths");

    TestCommon.run(
        "-verbose:class",
        "-cp", cp_exec,
        "-XX:+IgnoreEmptyClassPaths", // should affect classpath even if placed after the "-cp" argument
        "-Xlog:class+path=info",
        "HelloMore")
      .assertNormalExit();
  }
}
