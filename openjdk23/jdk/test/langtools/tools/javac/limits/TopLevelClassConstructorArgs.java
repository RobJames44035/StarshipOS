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
 * @run main TopLevelClassConstructorArgs
 */

public class TopLevelClassConstructorArgs extends NumArgsTest {
    private TopLevelClassConstructorArgs() {
        super(254, "TopLevelClassConstructorArgs");
    }

    public static void main(String... args) throws Exception {
        new TopLevelClassConstructorArgs().runTest();
    }
}
