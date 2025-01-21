/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8345911
 * @library /test/lib
 * @compile SealedSuper.java SealedSub.java
 * @comment Copy SealedSuper.class to the currnet directory so it will be on the bootclasspath
 * @run driver jdk.test.lib.helpers.ClassFileInstaller SealedSuper
 * @run main/othervm -Xbootclasspath/a:. -Xlog:class+sealed=trace SealedDifferentUnnamedModuleTest
 */

public class SealedDifferentUnnamedModuleTest {

    public static void main(String args[]) throws Throwable {

        // Load the sealed superclass. It will be loaded by the boot loader and
        // so reside in the boot loaders un-named module.
        Class<?> c1 = Class.forName("SealedSuper");

        // Test loading a "permitted" subclass in the app classloader, which then resides
        // in the app loader's un-named module.
        // This should fail.
        try {
            Class<?> c2 = Class.forName("SealedSub");
            throw new RuntimeException("Expected IncompatibleClassChangeError exception not thrown");
        } catch (IncompatibleClassChangeError e) {
            if (!e.getMessage().equals("Failed same module check: subclass SealedSub is in module 'unnamed module' " +
                                       "with loader 'app', and sealed class SealedSuper is in module 'unnamed module' " +
                                       "with loader 'bootstrap'")) {
                throw new RuntimeException("Wrong IncompatibleClassChangeError exception thrown: " + e.getMessage());
            }
        }
    }
}
