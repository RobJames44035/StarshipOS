/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:-ShenandoahPacing -Xmx128m TestPacing
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+ShenandoahPacing -Xmx128m TestPacing
 */

public class TestPacing {
    static final long TARGET_MB = Long.getLong("target", 1000); // 1 Gb allocation

    static volatile Object sink;

    public static void main(String[] args) throws Exception {
        long count = TARGET_MB * 1024 * 1024 / 16;
        for (long c = 0; c < count; c++) {
            sink = new Object();
        }
    }
}
