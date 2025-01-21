/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8179950
 * @build CustomLoader InitSystemLoaderTest
 * @run main/othervm -Djava.system.class.loader=CustomLoader InitSystemLoaderTest
 * @summary Test custom system loader initialization and verify their ancestors
 */

public class InitSystemLoaderTest {
    public static void main(String... args) {
        // check that system class loader is the custom loader
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        if (loader != CustomLoader.INSTANCE) {
            throw new RuntimeException("Expected custom loader: "
                + CustomLoader.INSTANCE + " got: " + loader);
        }

        // parent of the custom loader should be builtin system class loader
        ClassLoader builtinSystemLoader = loader.getParent();
        ClassLoader grandparent = builtinSystemLoader.getParent();
        if (grandparent != ClassLoader.getPlatformClassLoader()) {
            throw new RuntimeException("Expected class loader ancestor: "
                + ClassLoader.getPlatformClassLoader() + " got: " + grandparent);
        }
    }
}
