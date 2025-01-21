/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8313816
 * @summary Test that a sequence of method retransformation and stacktrace capture while the old method
 *          version is still on stack does not lead to a crash when that method's jmethodID is used as
 *          an argument for JVMTI functions.
 * @requires vm.jvmti
 * @requires vm.flagless
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @modules java.instrument
 *          java.compiler
 * @compile GetStackTraceAndRetransformTest.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main RedefineClassHelper
 * @run main/othervm/native -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -javaagent:redefineagent.jar -agentlib:GetStackTraceAndRetransformTest GetStackTraceAndRetransformTest
 */

import jdk.test.whitebox.WhiteBox;

class Transformable {
  static final String newClass = """
    class Transformable {
      static final String newClass = "";
      static void redefineAndStacktrace() throws Exception {}
      static void stacktrace() throws Exception {
        capture(Thread.currentThread());
      }
      public static native void capture(Thread thread);
    }
  """;
  static void redefineAndStacktrace() throws Exception {
    // This call will cause the class to be retransformed.
    // However, this method is still on stack so the subsequent attempt to capture the stacktrace
    // will result into this frame being identified by the jmethodID of the previous method version.
    RedefineClassHelper.redefineClass(Transformable.class, newClass);
    capture(Thread.currentThread());
  }

  static void stacktrace() throws Exception {
  }

  public static native void capture(Thread thread);
}

public class GetStackTraceAndRetransformTest {
    public static void main(String args[]) throws Throwable {
        initialize(Transformable.class);

        Transformable.redefineAndStacktrace();
        Transformable.stacktrace();

        WhiteBox.getWhiteBox().cleanMetaspaces();
        check(2);
    }

    public static native void initialize(Class<?> target);
    public static native void check(int expected);
}
