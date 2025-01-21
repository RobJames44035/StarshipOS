/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test ConstantGettersTransitionsTest
 * @summary Test the correctness of compilation level transitions for constant getters methods
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.tiered.ConstantGettersTransitionsTest
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm/timeout=240 -Xmixed -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *      -XX:+WhiteBoxAPI -XX:+TieredCompilation
 *      -XX:CompileCommand=compileonly,compiler.tiered.ConstantGettersTransitionsTest$ConstantGettersTestCase$TrivialMethods::*
 *      compiler.tiered.ConstantGettersTransitionsTest
 */

package compiler.tiered;

import compiler.whitebox.CompilerWhiteBoxTest;
import jtreg.SkippedException;

import java.lang.reflect.Executable;
import java.util.concurrent.Callable;

public class ConstantGettersTransitionsTest extends LevelTransitionTest {
    public static void main(String[] args) {
        if (CompilerWhiteBoxTest.skipOnTieredCompilation(false)) {
            throw new SkippedException("Test isn't applicable for non-tiered mode");
        }

        // run test cases
        for (TestCase testCase : ConstantGettersTestCase.values()) {
            new ConstantGettersTransitionsTest(testCase).runTest();
        }
    }

    @Override
    protected boolean isTrivial() {
        return true;
    }

    private ConstantGettersTransitionsTest(TestCase testCase) {
        super(testCase);
    }

    private static enum ConstantGettersTestCase implements CompilerWhiteBoxTest.TestCase {
        ICONST_M1,
        ICONST_0,
        ICONST_1,
        ICONST_2,
        ICONST_3,
        ICONST_4,
        ICONST_5,
        LCONST_0,
        LCONST_1,
        FCONST_0,
        FCONST_1,
        FCONST_2,
        DCONST_0,
        DCONST_1,
        DCONST_W,
        BYTE,
        SHORT,
        CHAR;

        private final Executable executable;
        private final Callable<Integer> callable;

        @Override
        public Executable getExecutable() {
            return executable;
        }

        @Override
        public Callable<Integer> getCallable() {
            return callable;
        }

        @Override
        public boolean isOsr() {
            return false;
        }

        private ConstantGettersTestCase() {
            String name = "make" + this.name();
            this.executable = MethodHelper.getMethod(TrivialMethods.class, name);
            this.callable = MethodHelper.getCallable(new TrivialMethods(), name);
        }

        /**
         * Contains methods that load constants with certain types of bytecodes
         * See JVMS 2.11.2. Load and Store Instructions
         * Note that it doesn't have a method for ldc_w instruction
         */
        private static class TrivialMethods {
            public static int makeICONST_M1() {
                return -1;
            }

            public static int makeICONST_0() {
                return 0;
            }

            public static int makeICONST_1() {
                return 1;
            }

            public static int makeICONST_2() {
                return 2;
            }

            public static int makeICONST_3() {
                return 3;
            }

            public static int makeICONST_4() {
                return 4;
            }

            public static int makeICONST_5() {
                return 5;
            }

            public static long makeLCONST_0() {
                return 0L;
            }

            public static long makeLCONST_1() {
                return 1L;
            }

            public static float makeFCONST_0() {
                return 0F;
            }

            public static float makeFCONST_1() {
                return 1F;
            }

            public static float makeFCONST_2() {
                return 2F;
            }

            public static double makeDCONST_0() {
                return 0D;
            }

            public static double makeDCONST_1() {
                return 1D;
            }

            public static double makeDCONST_W() {
                // ldc2_w
                return Double.MAX_VALUE;
            }

            public static Object makeOBJECT() {
                // aconst_null
                return null;
            }

            public static byte makeBYTE() {
                // bipush
                return (byte) 0x7F;
            }

            public static short makeSHORT() {
                // sipush
                return (short) 0x7FFF;
            }

            public static char makeCHAR() {
                // ldc
                return (char) 0xFFFF;
            }

            public static boolean makeBOOLEAN() {
                return true;
            }
        }
    }
}
