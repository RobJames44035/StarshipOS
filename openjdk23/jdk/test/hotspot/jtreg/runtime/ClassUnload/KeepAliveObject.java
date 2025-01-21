/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test KeepAliveObject
 * @summary This test case uses a class instance to keep the class alive.
 * @requires vm.opt.final.ClassUnloading
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library classes
 * @build jdk.test.whitebox.WhiteBox test.Empty
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmn8m -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI KeepAliveObject
 */

import jdk.test.whitebox.WhiteBox;
import jdk.test.lib.classloader.ClassUnloadCommon;
import java.util.List;
import java.util.Set;

import java.lang.ref.Reference;
/**
 * Test that verifies that classes are not unloaded when specific types of references are kept to them.
 */
public class KeepAliveObject {
  private static final String className = "test.Empty";
  private static final WhiteBox wb = WhiteBox.getWhiteBox();

  public static void main(String... args) throws Exception {
    ClassLoader cl = ClassUnloadCommon.newClassLoader();
    Class<?> c = cl.loadClass(className);
    Object o = c.newInstance();
    cl = null; c = null;

    {
        boolean isAlive = wb.isClassAlive(className);
        System.out.println("testObject (1) alive: " + isAlive);

        ClassUnloadCommon.failIf(!isAlive, "should be alive");
    }

    ClassUnloadCommon.triggerUnloading();

    {
        boolean isAlive = wb.isClassAlive(className);
        System.out.println("testObject (2) alive: " + isAlive);

        ClassUnloadCommon.failIf(!isAlive, "should be alive");
    }
    // Don't let `o` get prematurely reclaimed by the GC.
    Reference.reachabilityFence(o);
    o = null;

    Set<String> aliveClasses = ClassUnloadCommon.triggerUnloading(List.of(className));
    ClassUnloadCommon.failIf(!aliveClasses.isEmpty(), "testObject (3) should be unloaded: " + aliveClasses);
  }
}
