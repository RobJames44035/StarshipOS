/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary C2 should use ldar, stlr and ldaxr+stlxr insns for volatile operations
 * @library /test/lib /
 *
 * @modules java.base/jdk.internal.misc
 *
 * @requires vm.flagless
 * @requires os.arch=="aarch64" & vm.debug == true &
 *           vm.flavor == "server" & !vm.graal.enabled &
 *           vm.gc.Parallel
 *
 * @build compiler.c2.aarch64.TestVolatiles
 *        compiler.c2.aarch64.TestVolatileLoad
 *        compiler.c2.aarch64.TestUnsafeVolatileLoad
 *        compiler.c2.aarch64.TestVolatileStore
 *        compiler.c2.aarch64.TestUnsafeVolatileStore
 *        compiler.c2.aarch64.TestUnsafeVolatileCAS
 *        compiler.c2.aarch64.TestUnsafeVolatileWeakCAS
 *        compiler.c2.aarch64.TestUnsafeVolatileCAE
 *        compiler.c2.aarch64.TestUnsafeVolatileGAS
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestVolatileLoad Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestVolatileStore Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestUnsafeVolatileLoad Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestUnsafeVolatileStore Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestUnsafeVolatileCAS Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestUnsafeVolatileWeakCAS Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestUnsafeVolatileCAE Parallel
 *
 * @run driver compiler.c2.aarch64.TestVolatilesParallel
 *      TestUnsafeVolatileGAS Parallel
 */

package compiler.c2.aarch64;

public class TestVolatilesParallel {
    public static void main(String args[]) throws Throwable
    {
        // delegate work to shared code
        new TestVolatiles().runtest(args[0], args[1]);
    }
}
