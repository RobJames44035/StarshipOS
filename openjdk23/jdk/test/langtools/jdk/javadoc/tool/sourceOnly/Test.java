/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4548768
 * @summary Javadoc in JDK 1.4 uses classpath and not just source dir
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @compile p/SourceOnly.java p/NonSource.jasm
 * @run main Test
 */

public class Test {
    public static void main(String[] args) {
        // run javadoc on package p
        String[] jdargs = {
            "-doclet", "p.SourceOnly",
            "-docletpath", System.getProperty("test.classes", "."),
            "p"
        };
        if (jdk.javadoc.internal.tool.Main.execute(jdargs) != 0)
            throw new Error();
    }
}
