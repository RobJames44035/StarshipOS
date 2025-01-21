/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/jdk.internal.misc
 * @library /test/lib ..
 * @compile p2/c2.java
 * @build jdk.test.whitebox.WhiteBox
 * @compile/module=java.base java/lang/ModuleHelper.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI AccessCheckJavaBase
 */

import static jdk.test.lib.Asserts.*;

public class AccessCheckJavaBase {

    // Test that a class defined to module_two always can read java.base.
    public static void main(String args[]) throws Throwable {
        // Get the class loader for AccessCheckJavaBase and assume it's also used to
        // load class p2.c2.
        ClassLoader this_cldr = AccessCheckJavaBase.class.getClassLoader();

        // Define a module for p2.
        Object m2x = ModuleHelper.ModuleObject("module_two", this_cldr, new String[] { "p2" });
        assertNotNull(m2x, "Module should not be null");
        ModuleHelper.DefineModule(m2x, false, "9.0", "m2x/there", new String[] { "p2" });

        // p2.c2 can read its superclass java.lang.Object defined within java.base
        try {
            Class p2_c2_class = Class.forName("p2.c2");
        } catch (IllegalAccessError e) {
            throw new RuntimeException("Test Failed" + e.getMessage());
        }
    }
}
