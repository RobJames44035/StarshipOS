/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Hello World test for AppCDS
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @run driver HelloTest
 */

public class HelloTest {
  public static void main(String[] args) throws Exception {
      TestCommon.test(JarBuilder.getOrCreateHelloJar(),
          TestCommon.list("Hello"), "Hello");
  }
}
