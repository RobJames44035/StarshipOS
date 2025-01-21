/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4309152
 * @summary Compiler silently generates bytecode that exceeds VM limits
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 * @compile NumArgsTest.java
 * @run main TopLevelClassStaticMethodArgs
 */

public class TopLevelClassStaticMethodArgs extends NumArgsTest {
    private TopLevelClassStaticMethodArgs() {
        super(255, true, "void", "test", "TopLevelClassStaticMethodArgs");
    }

    public static void main(String... args) throws Exception {
        new TopLevelClassStaticMethodArgs().runTest();
    }
}
