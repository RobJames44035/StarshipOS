/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Platform;

/*
 * @test
 * @bug 8129855
 * @summary -XX:+IgnoreUnrecognizedVMOptions should work according to the spec from JDK-8129855
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver IgnoreUnrecognizedVMOptions
 */
public class IgnoreUnrecognizedVMOptions {

  private static void runJavaAndCheckExitValue(boolean shouldSucceed, String... args) throws Exception {
    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(args);
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    if (shouldSucceed) {
      output.shouldHaveExitValue(0);
    } else {
      output.shouldHaveExitValue(1);
    }
  }

  public static void main(String[] args) throws Exception {
    boolean product = !Platform.isDebugBuild();

    /*
      #1.1 wrong value and non-existing flag:
                                    exists, invalid value           does not exist
                                    -XX:MinHeapFreeRatio=notnum     -XX:THIS_FLAG_DOESNT_EXIST
      -IgnoreUnrecognizedVMOptions               ERR                           ERR
      +IgnoreUnrecognizedVMOptions               ERR                           OK
    */
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:MinHeapFreeRatio=notnum", "-version");
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:THIS_FLAG_DOESNT_EXIST", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:MinHeapFreeRatio=notnum", "-version");
    runJavaAndCheckExitValue(true, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:THIS_FLAG_DOESNT_EXIST", "-version");

    /*
      #1.2 normal flag with ranges:
                                      exists, in range                exists, out of range
                                      -XX:StackRedPages=1             -XX:StackRedPages=0
      -IgnoreUnrecognizedVMOptions               OK                            ERR
      +IgnoreUnrecognizedVMOptions               OK                            ERR
    */
    runJavaAndCheckExitValue(true, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:StackRedPages=1", "-version");
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:StackRedPages=0", "-version");
    runJavaAndCheckExitValue(true, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:StackRedPages=1", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:StackRedPages=0", "-version");

    /*
      #1.3 develop flag on debug VM:
                                      develop & !product_build
                                      -XX:+DeoptimizeALot
      -IgnoreUnrecognizedVMOptions               OK
      +IgnoreUnrecognizedVMOptions               OK
    */
    if (!product) {
      runJavaAndCheckExitValue(true, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:+DeoptimizeALot", "-version");
      runJavaAndCheckExitValue(true, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:+DeoptimizeALot", "-version");
    }

    /*
      #1.4 develop flag on product VM:
                                    develop & product_build
                                    -XX:+DeoptimizeALot
      -IgnoreUnrecognizedVMOptions               ERR
      +IgnoreUnrecognizedVMOptions               OK
    */
    if (product) {
      runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:+DeoptimizeALot", "-version");
      runJavaAndCheckExitValue(true, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:+DeoptimizeALot", "-version");
    }


    /*
      #1.5 malformed develop flag on debug VM:
                                  develop & !product_build
                                  -XX:DeoptimizeALot
      -IgnoreUnrecognizedVMOptions               ERR
      +IgnoreUnrecognizedVMOptions               ERR
    */
    if (!product) {
      runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:DeoptimizeALot", "-version");
      runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:DeoptimizeALot", "-version");
    }

    /*
      #1.6 malformed develop flag on product VM:
                                    develop & !product_build
                                    -XX:DeoptimizeALot
      -IgnoreUnrecognizedVMOptions               ERR
      +IgnoreUnrecognizedVMOptions               OK
    */
    if (product) {
      runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:DeoptimizeALot", "-version");
      runJavaAndCheckExitValue(true, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:DeoptimizeALot", "-version");
    }

    /*
      #1.7 locked flag:
                                      diagnostic & locked             experimental & locked
                                      -XX:-UnlockDiagnosticVMOptions  -XX:-UnlockExperimentalVMOptions
                                      -XX:+PrintInlining              -XX:+AlwaysSafeConstructors
      -IgnoreUnrecognizedVMOptions               ERR                           ERR
      +IgnoreUnrecognizedVMOptions               ERR                           ERR
    */
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:-UnlockDiagnosticVMOptions", "-XX:+PrintInlining", "-version");
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:-UnlockExperimentalVMOptions", "-XX:+AlwaysSafeConstructors", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:-UnlockDiagnosticVMOptions", "-XX:+PrintInlining", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:-UnlockExperimentalVMOptions", "-XX:+AlwaysSafeConstructors", "-version");

    /*
      #1.8 malformed locked flag:
                                    diagnostic & locked             experimental & locked
                                    -XX:-UnlockDiagnosticVMOptions  -XX:-UnlockExperimentalVMOptions
                                    -XX:PrintInlining               -XX:AlwaysSafeConstructors
      -IgnoreUnrecognizedVMOptions               ERR                           ERR
      +IgnoreUnrecognizedVMOptions               ERR                           ERR
    */
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:-UnlockDiagnosticVMOptions", "-XX:PrintInlining", "-version");
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:-UnlockExperimentalVMOptions", "-XX:AlwaysSafeConstructors", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:-UnlockDiagnosticVMOptions", "-XX:PrintInlining", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:-UnlockExperimentalVMOptions", "-XX:AlwaysSafeConstructors", "-version");

    /*
      #1.9 malformed unlocked flag:
                                    diagnostic & locked             experimental & locked
                                    -XX:+UnlockDiagnosticVMOptions  -XX:+UnlockExperimentalVMOptions
                                    -XX:PrintInlining               -XX:AlwaysSafeConstructors
      -IgnoreUnrecognizedVMOptions               ERR                           ERR
      +IgnoreUnrecognizedVMOptions               ERR                           ERR
    */
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:+UnlockDiagnosticVMOptions", "-XX:PrintInlining", "-version");
    runJavaAndCheckExitValue(false, "-XX:-IgnoreUnrecognizedVMOptions", "-XX:+UnlockExperimentalVMOptions", "-XX:AlwaysSafeConstructors", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:+UnlockDiagnosticVMOptions", "-XX:PrintInlining", "-version");
    runJavaAndCheckExitValue(false, "-XX:+IgnoreUnrecognizedVMOptions", "-XX:+UnlockExperimentalVMOptions", "-XX:AlwaysSafeConstructors", "-version");
  }
}
