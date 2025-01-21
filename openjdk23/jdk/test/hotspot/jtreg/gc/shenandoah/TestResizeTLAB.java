/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test id=passive
 * @key randomness
 * @summary Test that Shenandoah is able to work with(out) resizeable TLABs
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 */

/*
 * @test id=aggressive
 * @key randomness
 * @summary Test that Shenandoah is able to work with(out) resizeable TLABs
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 */

/*
 * @test id=adaptive
 * @key randomness
 * @summary Test that Shenandoah is able to work with(out) resizeable TLABs
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 */

/*
 * @test id=generational
 * @key randomness
 * @summary Test that Shenandoah is able to work with(out) resizeable TLABs
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 */

/*
 * @test id=static
 * @key randomness
 * @summary Test that Shenandoah is able to work with(out) resizeable TLABs
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 */

/*
 * @test id=compact
 * @key randomness
 * @summary Test that Shenandoah is able to work with(out) resizeable TLABs
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      -XX:+ShenandoahVerify
 *      -XX:+ResizeTLAB
 *      TestResizeTLAB
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      -XX:+ShenandoahVerify
 *      -XX:-ResizeTLAB
 *      TestResizeTLAB
 */

import java.util.Random;
import jdk.test.lib.Utils;

public class TestResizeTLAB {

    static final long TARGET_MB = Long.getLong("target", 10_000); // 10 Gb allocation

    static volatile Object sink;

    public static void main(String[] args) throws Exception {
        final int min = 0;
        final int max = 384 * 1024;
        long count = TARGET_MB * 1024 * 1024 / (16 + 4 * (min + (max - min) / 2));

        Random r = Utils.getRandomInstance();
        for (long c = 0; c < count; c++) {
            sink = new int[min + r.nextInt(max - min)];
        }
    }

}
