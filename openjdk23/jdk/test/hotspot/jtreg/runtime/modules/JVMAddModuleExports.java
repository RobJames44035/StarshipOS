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
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI JVMAddModuleExports
 */

import static jdk.test.lib.Asserts.*;

public class JVMAddModuleExports {

    public static void main(String args[]) throws Throwable {
        MyClassLoader from_cl = new MyClassLoader();
        MyClassLoader to_cl = new MyClassLoader();
        Module from_module, to_module;

        from_module = ModuleHelper.ModuleObject("from_module", from_cl, new String[] { "mypackage", "x/apackage" });
        assertNotNull(from_module, "Module should not be null");
        ModuleHelper.DefineModule(from_module, false, "9.0", "from_module/here", new String[] { "mypackage", "x/apackage" });
        to_module = ModuleHelper.ModuleObject("to_module", to_cl, new String[] { "yourpackage", "that/apackage" });
        assertNotNull(to_module, "Module should not be null");
        ModuleHelper.DefineModule(to_module, false, "9.0", "to_module/here", new String[] { "yourpackage", "that/apackage" });

        // Null from_module argument, expect an NPE
        try {
            ModuleHelper.AddModuleExports((Module)null, "mypackage", to_module);
            throw new RuntimeException("Failed to get the expected NPE for null from_module");
        } catch(NullPointerException e) {
            // Expected
        }

        // Null to_module argument, expect an NPE
        try {
            ModuleHelper.AddModuleExports(from_module, "mypackage", (Module)null);
            throw new RuntimeException("Failed to get the expected NPE for null to_module");
        } catch(NullPointerException e) {
            // Expected
        }

        // Bad from_module argument, expect an IAE
        try {
            ModuleHelper.AddModuleExports(to_cl, "mypackage", to_module);
            throw new RuntimeException("Failed to get the expected IAE");
        } catch(IllegalArgumentException e) {
            // Expected
        }

        // Null package argument, expect an NPE
        try {
            ModuleHelper.AddModuleExports(from_module, null, to_module);
            throw new RuntimeException("Failed to get the expected NPE");
        } catch(NullPointerException e) {
            // Expected
        }

        // Bad to_module argument, expect an IAE
        try {
            ModuleHelper.AddModuleExports(from_module, "mypackage", from_cl);
            throw new RuntimeException("Failed to get the expected IAE");
        } catch(IllegalArgumentException e) {
            // Expected
        }

        // Exporting a package to the same module
        ModuleHelper.AddModuleExports(from_module, "mypackage", from_module);

        // Export a package that does not exist to to_module
        try {
            ModuleHelper.AddModuleExports(from_module, "notmypackage", to_module);
            throw new RuntimeException("Failed to get the expected IAE");
        } catch(IllegalArgumentException e) {
            // Expected
        }

        // Export a package, that is not in from_module, to to_module
        try {
            ModuleHelper.AddModuleExports(from_module, "yourpackage", to_module);
            throw new RuntimeException("Failed to get the expected IAE");
        } catch(IllegalArgumentException e) {
            // Expected
        }

        // Export a package, that does not exist, to from_module
        try {
            ModuleHelper.AddModuleExports(from_module, "notmypackage", from_module);
            throw new RuntimeException("Failed to get the expected IAE");
        } catch(IllegalArgumentException e) {
            // Expected
        }

        // Export a package, that is not in from_module, to from_module
        try {
            ModuleHelper.AddModuleExports(from_module, "that/apackage", from_module);
            throw new RuntimeException("Failed to get the expected IAE");
        } catch(IllegalArgumentException e) {
            // Expected
        }

        // Export the same package twice to the same module
        ModuleHelper.AddModuleExports(from_module, "x/apackage", to_module);
        ModuleHelper.AddModuleExports(from_module, "x/apackage", to_module);

        // Export the same package, using '.' instead of '/'
        ModuleHelper.AddModuleExports(from_module, "x.apackage", to_module);

        // Export a package to the unnamed module and then to a specific module.
        // The qualified export should be ignored.
        ModuleHelper.AddModuleExportsToAll(to_module, "that/apackage");
        ModuleHelper.AddModuleExports(to_module, "that/apackage", from_module);
    }

    static class MyClassLoader extends ClassLoader { }
}
