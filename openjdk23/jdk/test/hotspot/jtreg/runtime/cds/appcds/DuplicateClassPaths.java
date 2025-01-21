/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary duplicate class paths test
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @run driver DuplicateClassPaths
 */

import java.io.File;

public class DuplicateClassPaths {
    public static void main(String[] args) throws Exception {
        String appJar = JarBuilder.getOrCreateHelloJar();
        String jars = appJar + File.pathSeparator + appJar;
        TestCommon.test(jars, TestCommon.list("Hello"), "Hello");
    }
}
