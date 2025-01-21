/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test id=default
 * @summary Shenandoah crashes with -XX:ObjectAlignmentInBytes=16
 * @key randomness
 * @requires vm.gc.Shenandoah
 * @requires vm.bits == "64"
 * @library /test/lib
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ObjectAlignmentInBytes=16 -Xint                   TestLargeObjectAlignment
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ObjectAlignmentInBytes=16 -XX:-TieredCompilation  TestLargeObjectAlignment
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ObjectAlignmentInBytes=16 -XX:TieredStopAtLevel=1 TestLargeObjectAlignment
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ObjectAlignmentInBytes=16 -XX:TieredStopAtLevel=4 TestLargeObjectAlignment
 */

/*
 * @test id=generational
 * @summary Shenandoah crashes with -XX:ObjectAlignmentInBytes=16
 * @key randomness
 * @requires vm.gc.Shenandoah
 * @requires vm.bits == "64"
 * @library /test/lib
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:ObjectAlignmentInBytes=16 -Xint                   TestLargeObjectAlignment
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:ObjectAlignmentInBytes=16 -XX:-TieredCompilation  TestLargeObjectAlignment
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:ObjectAlignmentInBytes=16 -XX:TieredStopAtLevel=1 TestLargeObjectAlignment
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:ObjectAlignmentInBytes=16 -XX:TieredStopAtLevel=4 TestLargeObjectAlignment
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jdk.test.lib.Utils;

public class TestLargeObjectAlignment {

    static final int SLABS_COUNT = Integer.getInteger("slabs", 10000);
    static final int NODE_COUNT = Integer.getInteger("nodes", 10000);
    static final long TIME_NS = 1000L * 1000L * Integer.getInteger("timeMs", 5000);

    static Object[] objects;

    public static void main(String[] args) throws Exception {
        objects = new Object[SLABS_COUNT];

        long start = System.nanoTime();
        Random rng = Utils.getRandomInstance();
        while (System.nanoTime() - start < TIME_NS) {
            objects[rng.nextInt(SLABS_COUNT)] = createSome();
        }
    }

    public static Object createSome() {
        List<Integer> result = new ArrayList<Integer>();
        for (int c = 0; c < NODE_COUNT; c++) {
            result.add(new Integer(c));
        }
        return result;
    }

}
