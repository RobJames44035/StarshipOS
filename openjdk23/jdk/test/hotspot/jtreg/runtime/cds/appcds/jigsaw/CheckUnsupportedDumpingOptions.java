/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Abort dumping if any of the new jigsaw vm options is specified.
 * @requires vm.cds
 * @library /test/lib ..
 * @compile ../test-classes/Hello.java
 * @run driver CheckUnsupportedDumpingOptions
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class CheckUnsupportedDumpingOptions {
    private static final String[] jigsawOptions = {
        "--limit-modules",
        "--upgrade-module-path",
        "--patch-module"
    };
    private static final String[] optionValues = {
        "mymod",
        ".",
        "java.naming=javax.naming.spi.NamingManger"
    };

    public static void main(String[] args) throws Exception {
        String source = "package javax.naming.spi; "                +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass!\"); " +
                        "    } "                                    +
                        "}";
        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager",
            InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager", source, "--patch-module=java.naming"),
            "mods/java.naming");

        JarBuilder.build("hello", "Hello");
        String appJar = TestCommon.getTestJar("hello.jar");
        String appClasses[] = {"Hello"};
        for (int i = 0; i < jigsawOptions.length; i++) {
            OutputAnalyzer output;
            output = TestCommon.dump(appJar, appClasses, "-Xlog:cds,cds+hashtables",
                                     jigsawOptions[i], optionValues[i]);
            output.shouldContain("Cannot use the following option " +
                "when dumping the shared archive: " + jigsawOptions[i])
                  .shouldHaveExitValue(1);
        }
    }
}
