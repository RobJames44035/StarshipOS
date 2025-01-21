/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8279258
 * @summary Auto-vectorization enhancement for two-dimensional array operations
 * @requires ((os.arch == "x86" | os.arch == "i386") & (vm.opt.UseSSE == "null" | vm.opt.UseSSE >= 2))
 *           | (os.arch != "x86" & os.arch != "i386" & os.arch != "riscv64")
 *           | (os.arch == "riscv64" & vm.opt.UseRVV == true)
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestAutoVectorization2DArray
 */

public class TestAutoVectorization2DArray {
    final private static int NUM = 64;

    private static double[][] a = new double[NUM][NUM];
    private static double[][] b = new double[NUM][NUM];
    private static double[][] c = new double[NUM][NUM];

    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    // Given small iteration count, the unrolling factor is not very predictable,
    // hence it is difficult to exactly predict the vector size. But let's at least
    // check that there is some vectorization of any size.
    @IR(counts = { IRNode.LOAD_VECTOR_D, IRNode.VECTOR_SIZE_ANY, " >0 " })
    @IR(counts = { IRNode.ADD_VD,        IRNode.VECTOR_SIZE_ANY, " >0 " })
    @IR(counts = { IRNode.STORE_VECTOR,                          " >0 " })
    private static void testDouble(double[][] a , double[][] b, double[][] c) {
        for(int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = b[i][j] + c[i][j];
            }
        }
    }

    @Run(test = "testDouble")
    private void testDouble_runner() {
        testDouble(a, b, c);
    }
}
