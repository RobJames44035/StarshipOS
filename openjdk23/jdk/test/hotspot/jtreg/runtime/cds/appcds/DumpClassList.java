/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary DumpLoadedClassList should exclude generated classes, classes in bootclasspath/a and
 *          --patch-module.
 * @requires vm.cds
 * @modules jdk.jfr
 * @library /test/lib
 * @compile test-classes/DumpClassListApp.java
 * @run driver DumpClassList
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.helpers.ClassFileInstaller;

public class DumpClassList {
    public static void main(String[] args) throws Exception {
        // build The app
        String[] appClass = new String[] {"DumpClassListApp"};
        String classList = "app.list";

        JarBuilder.build("app", appClass[0]);
        String appJar = TestCommon.getTestJar("app.jar");

        // build patch-module
        String source = "package jdk.jfr; "                         +
                        "public class NewClass { "                  +
                        "    static { "                             +
                        "        System.out.println(\"NewClass\"); "+
                        "    } "                                    +
                        "}";

        ClassFileInstaller.writeClassToDisk("jdk/jfr/NewClass",
             InMemoryJavaCompiler.compile("jdk.jfr.NewClass", source, "--patch-module=jdk.jfr"),
             System.getProperty("test.classes"));

        String patchJar = JarBuilder.build("jdk_jfr", "jdk/jfr/NewClass");

        // build bootclasspath/a
        String source2 = "package boot.append; "                 +
                        "public class Foo { "                    +
                        "    static { "                          +
                        "        System.out.println(\"Foo\"); "  +
                        "    } "                                 +
                        "}";

        ClassFileInstaller.writeClassToDisk("boot/append/Foo",
             InMemoryJavaCompiler.compile("boot.append.Foo", source2),
             System.getProperty("test.classes"));

        String appendJar = JarBuilder.build("bootappend", "boot/append/Foo");

        // dump class list
        CDSTestUtils.dumpClassList(classList,
                                   "--patch-module=jdk.jfr=" + patchJar,
                                   "-Xbootclasspath/a:" + appendJar,
                                   "-cp",
                                   appJar,
                                   appClass[0])
            .assertNormalExit(output -> {
                output.shouldContain("hello world");
            });

        CDSOptions opts = (new CDSOptions())
            .setClassList(appClass)
            .addPrefix("-cp", appJar,
                       "-Xbootclasspath/a:" + appendJar,
                       "-Xlog:class+load",
                       "-Xlog:cds+class=debug",
                       "-XX:SharedClassListFile=" + classList);
        CDSTestUtils.createArchiveAndCheck(opts)
            .shouldNotContain("Preload Warning: Cannot find java/lang/invoke/LambdaForm")
            .shouldNotContain("Preload Warning: Cannot find boot/append/Foo")
            .shouldNotContain("Preload Warning: Cannot find jdk/jfr/NewClass")
            .shouldMatch("class,load *. boot.append.Foo")      // from -Xlog:class+load
            .shouldMatch("cds,class.*boot  boot.append.Foo")   // from -Xlog:cds+class
            .shouldNotMatch("class,load *. jdk.jfr.NewClass"); // from -Xlog:class+load
    }
}
