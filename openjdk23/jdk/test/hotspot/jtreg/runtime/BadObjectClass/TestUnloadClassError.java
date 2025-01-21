/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
/*
 * @test
 * @bug 6194361
 * @bug 8202669
 * @summary Make sure the VM doesn't crash and throws a SecurityException
 *          if defineClass() is called on a byte buffer that parses into a invalid
 *          java.lang.Object class.
 *          Also, make sure the vm doesn't crash on notification for unloading an invalid
 *          java.lang.Object class.
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI TestUnloadClassError
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.classloader.ClassUnloadCommon;

public class TestUnloadClassError extends ClassLoader {

   static String source =
       " package java.lang;" +
       " public class Object" +
       " {" +
       "   int field;" +
       "   public boolean equals(Object o) {" +
       "     System.out.println(o.field);" +
       "     return false;" +
       "   }" +
       " }";

  public static void main(String[] args) throws Exception
  {
    try {
      TestUnloadClassError loader = new TestUnloadClassError();
      byte[] buf = InMemoryJavaCompiler.compile("java.lang.Object", source,
                                                "--patch-module=java.base");
      Class c = loader.defineClass(buf, 0, buf.length);
      System.out.println("test FAILS");
      throw new RuntimeException("Did not get security exception");
    } catch(SecurityException e) {
      System.out.println("test expects SecurityException");
    }

    // Unload bad class
    ClassUnloadCommon.triggerUnloading();
    System.out.println("test PASSES if it doesn't crash");
  }
}
