/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * Used with -p or --upgrade-module-path to exercise the replacement
 * of classes in modules that are linked into the runtime image.
 */

package jdk.test;

public class Main {
    static final ClassLoader PLATFORM_LOADER = ClassLoader.getPlatformClassLoader();
    static final ClassLoader SYS_LOADER      = ClassLoader.getSystemClassLoader();

    public static void main(String[] args) throws Exception {
        ClassLoader loader = null;
        boolean shouldOverride = false;

        /*
         * 3 Arguments are passed to this test:
         *   1. className: Name of the class to load.
         *   2. loaderName: Either "platform" or "app", which specifies which ClassLoader is expected
         *      to be the defining ClassLoader once the class is loaded. The initiating
         *      ClassLoader is always the default ClassLoader (which should be the
         *      app (system) ClassLoader.
         *   3. shouldOverride: Either "true" or "false" to indicate whether the loaded class
         *      should be the one we are attempting to override with (not the archived version).
         */

        assertTrue(args.length == 3, "Unexpected number of arguments: expected 3, actual " + args.length);
        String className = args[0].replace('/', '.');
        String loaderName = args[1]; // "platform" or "app"
        String shouldOverrideName = args[2];  // "true" or "false"

        if (loaderName.equals("app")) {
            loader = SYS_LOADER;
        } else if (loaderName.equals("platform")) {
            loader = PLATFORM_LOADER;
        } else {
            assertTrue(false);
        }

        if (shouldOverrideName.equals("true")) {
            shouldOverride = true;
        } else if (shouldOverrideName.equals("false")) {
            shouldOverride = false;
        } else {
            assertTrue(false);
        }

        // Load the class with the default ClassLoader.
        Class<?> clazz = Class.forName(className, true, loader);
        // Make sure we got the expected defining ClassLoader
        testLoader(clazz, loader);

        String s = null;
        if (shouldOverride) {
          // Create an instance and see what toString() returns
          clazz.newInstance().toString();
        }
        // The overridden version of the class should return "hi". Make sure
        // it does only if we are expecting to have loaded the overridden version.
        assertTrue("hi".equals(s) == shouldOverride);
    }

    /**
     * Asserts that given class has the expected defining loader.
     */
    static void testLoader(Class<?> clazz, ClassLoader expected) {
        ClassLoader loader = clazz.getClassLoader();
        if (loader != expected) {
            throw new RuntimeException(clazz + " loaded by " + loader + ", expected " + expected);
        }
    }

    static void assertTrue(boolean expr) {
        assertTrue(expr, "");
    }

    static void assertTrue(boolean expr, String msg) {
        if (!expr)
            throw new RuntimeException("assertion failed: " + msg);
    }
}
