/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies that AsyncGetCallTrace is call-able and provides sane information.
 * @compile ASGCTBaseTest.java
 * @requires os.family == "linux"
 * @requires os.arch=="x86" | os.arch=="i386" | os.arch=="amd64" | os.arch=="x86_64" | os.arch=="arm" | os.arch=="aarch64" | os.arch=="ppc64" | os.arch=="s390" | os.arch=="riscv64"
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:AsyncGetCallTraceTest MyPackage.ASGCTBaseTest
 */

public class ASGCTBaseTest {
  static {
    try {
      System.loadLibrary("AsyncGetCallTraceTest");
    } catch (UnsatisfiedLinkError ule) {
      System.err.println("Could not load AsyncGetCallTrace library");
      System.err.println("java.library.path: " + System.getProperty("java.library.path"));
      throw ule;
    }
  }

  private static native boolean checkAsyncGetCallTraceCall();

  public static void main(String[] args) {
    if (!checkAsyncGetCallTraceCall()) {
      throw new RuntimeException("AsyncGetCallTrace call failed.");
    }
  }
}
