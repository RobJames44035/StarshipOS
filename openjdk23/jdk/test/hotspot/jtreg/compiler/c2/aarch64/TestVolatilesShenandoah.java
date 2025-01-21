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
 *           vm.flavor == "server" &
 *           vm.gc.Shenandoah
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
 *        compiler.c2.aarch64.TestUnsafeVolatileGAA
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestVolatileLoad Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestVolatileStore Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileLoad Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileStore Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileCAS Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileWeakCAS Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileCAE Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileGAS Shenandoah
 *
 * @run driver compiler.c2.aarch64.TestVolatilesShenandoah
 *      TestUnsafeVolatileGAA Shenandoah
 *
 */

package compiler.c2.aarch64;

public class TestVolatilesShenandoah {
    public static void main(String args[]) throws Throwable
    {
        // delegate work to shared code
        new TestVolatiles().runtest(args[0], args[1]);
    }
}
