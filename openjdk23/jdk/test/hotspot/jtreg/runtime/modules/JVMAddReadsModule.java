/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/jdk.internal.misc
 * @library /test/lib ..
 * @build jdk.test.whitebox.WhiteBox
 * @compile/module=java.base java/lang/ModuleHelper.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI JVMAddReadsModule
 */

import static jdk.test.lib.Asserts.*;

public class JVMAddReadsModule {

    public static void main(String args[]) throws Throwable {
        MyClassLoader from_cl = new MyClassLoader();
        MyClassLoader to_cl = new MyClassLoader();
        Object from_module, to_module;

        from_module = ModuleHelper.ModuleObject("from_module", from_cl, new String[] { "mypackage" });
        assertNotNull(from_module, "Module should not be null");
        ModuleHelper.DefineModule(from_module, false, "9.0", "from_module/here", new String[] { "mypackage" });

        to_module = ModuleHelper.ModuleObject("to_module", to_cl, new String[] { "yourpackage" });
        assertNotNull(to_module, "Module should not be null");
        ModuleHelper.DefineModule(to_module, false, "9.0", "to_module/here", new String[] { "yourpackage" });

        // Null from_module argument, expect NPE
        try {
            ModuleHelper.AddReadsModule(null, to_module);
            throw new RuntimeException("Failed to get the expected NPE");
        } catch (NullPointerException e) {
            // Expected
        }

        // Null to_module argument, expect NPE
        try {
            ModuleHelper.AddReadsModule(from_module, null);
            throw new RuntimeException("Unexpected NPE was thrown");
        } catch (NullPointerException e) {
            // Expected
        }

        // Null from_module and to_module arguments, expect NPE
        try {
            ModuleHelper.AddReadsModule(null, null);
            throw new RuntimeException("Failed to get the expected NPE");
        } catch (NullPointerException e) {
            // Expected
        }

        // Both modules are the same, should not throw an exception
        ModuleHelper.AddReadsModule(from_module, from_module);

        // Duplicate calls, should not throw an exception
        ModuleHelper.AddReadsModule(from_module, to_module);
        ModuleHelper.AddReadsModule(from_module, to_module);
    }

    static class MyClassLoader extends ClassLoader { }
}
