/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc;

/*
 * @test id=Serial
 * @requires vm.gc.Serial
 * @summary Runs System.gc() with different flags.
 * @run main/othervm -XX:+UseSerialGC gc.TestSystemGC
 * @run main/othervm -XX:+UseSerialGC -XX:+UseLargePages gc.TestSystemGC
 */

/*
 * @test id=Parallel
 * @requires vm.gc.Parallel
 * @summary Runs System.gc() with different flags.
 * @run main/othervm -XX:+UseParallelGC gc.TestSystemGC
 * @run main/othervm -XX:+UseParallelGC -XX:+UseLargePages gc.TestSystemGC
 */

/*
 * @test id=G1
 * @requires vm.gc.G1
 * @summary Runs System.gc() with different flags.
 * @run main/othervm -XX:+UseG1GC gc.TestSystemGC
 * @run main/othervm -XX:+UseG1GC -XX:+ExplicitGCInvokesConcurrent gc.TestSystemGC
 * @run main/othervm -XX:+UseG1GC -XX:+UseLargePages gc.TestSystemGC
 */

/*
 * @test id=Shenandoah
 * @requires vm.gc.Shenandoah
 * @summary Runs System.gc() with different flags.
 * @run main/othervm -XX:+UseShenandoahGC gc.TestSystemGC
 * @run main/othervm -XX:+UseShenandoahGC -XX:+ExplicitGCInvokesConcurrent gc.TestSystemGC
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages gc.TestSystemGC
 */

/*
 * @test id=Z
 * @requires vm.gc.Z
 * @comment ZGC will not start when LargePages cannot be allocated, therefore
 *          we do not run such configuration.
 * @summary Runs System.gc() with different flags.
 * @run main/othervm -XX:+UseZGC gc.TestSystemGC
 */

public class TestSystemGC {
  public static void main(String args[]) throws Exception {
    System.gc();
  }
}
