/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test Class unloading test with concurrent mark
 * @summary Make sure that verification during gc does not prevent class unloading
 * @bug 8280454
 * @requires vm.gc.G1
 * @requires vm.opt.final.ClassUnloading
 * @requires vm.opt.final.ClassUnloadingWithConcurrentMark
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library classes
 * @build jdk.test.whitebox.WhiteBox test.Empty
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmn8m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+VerifyDuringGC -XX:+AlwaysTenure -XX:+WhiteBoxAPI -Xlog:gc,class+unload=debug UnloadTestWithVerifyDuringGC
 */
import jdk.test.whitebox.WhiteBox;
import jdk.test.lib.classloader.ClassUnloadCommon;

/**
 * Test that verifies that classes are unloaded using concurrent mark with G1 when they are no
 * longer reachable even when -XX:+VerifyDuringGC is enabled
 *
 * The test creates a class loader, uses the loader to load a class and creates an instance
 * of that class. The it nulls out all the references to the instance, class and class loader
 * and tries to trigger class unloading using a concurrent mark. Then it verifies that the class
 * is no longer loaded by the VM.
 */
public class UnloadTestWithVerifyDuringGC {
    private static final WhiteBox wb = WhiteBox.getWhiteBox();

    private static void triggerUnloadingWithConcurrentMark() throws Exception {
        // Try to unload classes using concurrent mark.
        wb.g1RunConcurrentGC();
    }

    private static String className = "test.Empty";

    public static void main(String... args) throws Exception {
        ClassUnloadCommon.failIf(wb.isClassAlive(className), "is not expected to be alive yet");

        ClassLoader cl = ClassUnloadCommon.newClassLoader();
        Class<?> c = cl.loadClass(className);
        Object o = c.newInstance();

        ClassUnloadCommon.failIf(!wb.isClassAlive(className), "should be live here");

        String loaderName = cl.getName();
        int loadedRefcount = wb.getSymbolRefcount(loaderName);
        System.out.println("Refcount of symbol " + loaderName + " is " + loadedRefcount);

        // Move everything into the old gen so that concurrent mark can unload.
        wb.youngGC();
        cl = null; c = null; o = null;
        triggerUnloadingWithConcurrentMark();

        ClassUnloadCommon.failIf(wb.isClassAlive(className), "should have been unloaded");

        int unloadedRefcount = wb.getSymbolRefcount(loaderName);
        System.out.println("Refcount of symbol " + loaderName + " is " + unloadedRefcount);
        ClassUnloadCommon.failIf(unloadedRefcount != (loadedRefcount - 1), "Refcount must be decremented");
   }
}
