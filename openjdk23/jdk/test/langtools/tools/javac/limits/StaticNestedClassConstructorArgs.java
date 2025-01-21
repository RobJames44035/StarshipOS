/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8014230
 * @summary Compiler silently generates bytecode that exceeds VM limits
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 * @compile NumArgsTest.java
 * @run main StaticNestedClassConstructorArgs
 */

public class StaticNestedClassConstructorArgs extends NumArgsTest {
    private static final NumArgsTest.NestingDef[] nesting = {
        classNesting("StaticInner", true)
    };

    private StaticNestedClassConstructorArgs() {
        super(254, "StaticNestedClassConstructorArgs", "StaticInner", nesting);
    }

    public static void main(String... args) throws Exception {
        new StaticNestedClassConstructorArgs().runTest();
    }
}

