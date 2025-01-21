/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Check Shenandoah reacts on setting humongous moves correctly
 * @key randomness
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -Xmx1g -Xms1g
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahHumongousMoves
 *      -XX:-ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      TestHumongousMoves
 *
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -Xmx1g -Xms1g
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahHumongousMoves
 *      -XX:-ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      TestHumongousMoves
 */

import java.util.Random;
import jdk.test.lib.Utils;

public class TestHumongousMoves {

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
