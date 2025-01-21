/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/jdk.internal.misc
 * @library /test/lib ..
 * @compile p2/c2.java
 * @compile p3/c3.java
 * @build jdk.test.whitebox.WhiteBox
 * @compile/module=java.base java/lang/ModuleHelper.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI AccessCheckSuper
 */

import static jdk.test.lib.Asserts.*;

public class AccessCheckSuper {

    // Test that when a class cannot access its super class the message
    // contains  both "superclass" text and module text.
    public static void main(String args[]) throws Throwable {

        // Get the class loader for AccessCheckSuper and assume it's also used to
        // load class p2.c2 and class p3.c3.
        ClassLoader this_cldr = AccessCheckSuper.class.getClassLoader();

        // Define a module for p2.
        Object m2x = ModuleHelper.ModuleObject("module_two", this_cldr, new String[] { "p2" });
        assertNotNull(m2x, "Module should not be null");
        ModuleHelper.DefineModule(m2x, false, "9.0", "m2x/there", new String[] { "p2" });

        // Define a module for p3.
        Object m3x = ModuleHelper.ModuleObject("module_three", this_cldr, new String[] { "p3" });
        assertNotNull(m3x, "Module should not be null");
        ModuleHelper.DefineModule(m3x, false, "9.0", "m3x/there", new String[] { "p3" });

        // Since a readability edge has not been established between module_two
        // and module_three, p3.c3 cannot read its superclass p2.c2.
        try {
            Class p3_c3_class = Class.forName("p3.c3");
            throw new RuntimeException("Failed to get IAE (can't read superclass)");
        } catch (IllegalAccessError e) {
            if (!e.getMessage().contains("superclass access check failed") ||
                !e.getMessage().contains("does not read")) {
                throw new RuntimeException("Wrong message: " + e.getMessage());
            }
        }
    }
}
