/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Ensure module information is cleaned when owning class loader unloads
 * @requires vm.opt.final.ClassUnloading
 * @modules java.base/jdk.internal.misc
 * @library /test/lib ..
 * @build jdk.test.whitebox.WhiteBox
 * @compile/module=java.base java/lang/ModuleHelper.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xms64m -Xmx64m LoadUnloadModuleStress 15000
 */

import java.lang.ref.WeakReference;

import static jdk.test.lib.Asserts.*;

public class LoadUnloadModuleStress {
    private static long timeout;
    private static long timeStamp;

    public static byte[] garbage;
    public static volatile WeakReference<MyClassLoader> clweak;

    public static Object createModule() throws Throwable {
        MyClassLoader cl = new MyClassLoader();
        Object module = ModuleHelper.ModuleObject("mymodule", cl, new String [] {"PackageA"});
        assertNotNull(module);
        ModuleHelper.DefineModule(module, false, "9.0", "mymodule", new String[] { "PackageA" });
        clweak = new WeakReference<>(cl);
        return module;
    }

    public static void main(String args[]) throws Throwable {
        timeout = Long.valueOf(args[0]);
        timeStamp = System.currentTimeMillis();

        while(System.currentTimeMillis() - timeStamp < timeout) {
            WeakReference<Object> modweak = new WeakReference<>(createModule());

            while(clweak.get() != null) {
                garbage = new byte[8192];
                System.gc();
            }
            assertNull(modweak.get());
        }
    }
    static class MyClassLoader extends ClassLoader { }
}
