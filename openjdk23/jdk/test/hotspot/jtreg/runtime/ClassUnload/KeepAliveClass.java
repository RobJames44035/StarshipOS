/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test KeepAliveClass
 * @summary This test case uses a java.lang.Class instance to keep a class alive.
 * @requires vm.opt.final.ClassUnloading
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library classes
 * @build jdk.test.whitebox.WhiteBox test.Empty
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmn8m -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI KeepAliveClass
 */

import java.lang.ref.SoftReference;
import jdk.test.whitebox.WhiteBox;
import jdk.test.lib.classloader.ClassUnloadCommon;
import java.util.List;
import java.util.Set;

/**
 * Test that verifies that classes are not unloaded when specific types of references are kept to them.
 */
public class KeepAliveClass {
  private static final String className = "test.Empty";
  private static final WhiteBox wb = WhiteBox.getWhiteBox();
  public static Object escape = null;

  public static void main(String... args) throws Exception {
    ClassLoader cl = ClassUnloadCommon.newClassLoader();
    Class<?> c = cl.loadClass(className);
    Object o = c.newInstance();
    o = null; cl = null;
    escape = c;

    {
        boolean isAlive = wb.isClassAlive(className);
        System.out.println("testClass (1) alive: " + isAlive);

        ClassUnloadCommon.failIf(!isAlive, "should be alive");
    }

    ClassUnloadCommon.triggerUnloading();
    {
        boolean isAlive = wb.isClassAlive(className);
        System.out.println("testClass (2) alive: " + isAlive);

        ClassUnloadCommon.failIf(!isAlive, "should be alive");
    }
    c = null;
    escape = null;

    Set<String> aliveClasses = ClassUnloadCommon.triggerUnloading(List.of(className));
    ClassUnloadCommon.failIf(!aliveClasses.isEmpty(), "testClass (3) should be unloaded: " + aliveClasses);
  }
}
