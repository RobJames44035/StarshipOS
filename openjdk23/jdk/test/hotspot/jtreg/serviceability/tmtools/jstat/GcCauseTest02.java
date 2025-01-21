/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test checks output displayed with jstat -gccause.
 *          Test scenario:
 *          test forces debuggee application eat ~70% of heap and runs jstat.
 *          jstat should show actual usage of old gen (OC/OU ~= old gen usage).
 * @requires vm.opt.ExplicitGCInvokesConcurrent != true
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies
                     -XX:+UsePerfData -XX:MaxNewSize=4m -XX:MaxHeapSize=128M -XX:MaxMetaspaceSize=128M GcCauseTest02
 */
import utils.*;

// This test produces more than 90_000 classes until it eats up ~70% of the 128M meta space.
// We turn off VerifyDependencies because it slows down the test considerably in debug builds.
public class GcCauseTest02 {

    public static void main(String[] args) throws Exception {
        new GarbageProducerTest(new JstatGcCauseTool(ProcessHandle.current().pid())).run();
    }
}
