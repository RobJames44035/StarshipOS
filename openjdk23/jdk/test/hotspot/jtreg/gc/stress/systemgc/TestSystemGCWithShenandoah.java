/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.stress.systemgc;

/*
 * @test id=default
 * @key stress
 * @library /
 * @requires vm.gc.Shenandoah
 * @summary Stress the Shenandoah GC full GC by allocating objects of different lifetimes concurrently with System.gc().
 *
 * @run main/othervm/timeout=300 -Xlog:gc*=info -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions
 *      -XX:+UseShenandoahGC
 *      -XX:+ShenandoahVerify
 *      gc.stress.systemgc.TestSystemGCWithShenandoah 270
 *
 * @run main/othervm/timeout=300 -Xlog:gc*=info -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions
 *      -XX:+UseShenandoahGC
 *      gc.stress.systemgc.TestSystemGCWithShenandoah 270
 */

/*
 * @test id=generational
 * @key stress
 * @library /
 * @requires vm.gc.Shenandoah
 * @summary Stress the Shenandoah GC full GC by allocating objects of different lifetimes concurrently with System.gc().
 *
 * @run main/othervm/timeout=300 -Xlog:gc*=info -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational
 *      -XX:+ShenandoahVerify
 *      gc.stress.systemgc.TestSystemGCWithShenandoah 270
 *
 * @run main/othervm/timeout=300 -Xlog:gc*=info -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational
 *      gc.stress.systemgc.TestSystemGCWithShenandoah 270
 */
public class TestSystemGCWithShenandoah {
    public static void main(String[] args) throws Exception {
        TestSystemGC.main(args);
    }
}
