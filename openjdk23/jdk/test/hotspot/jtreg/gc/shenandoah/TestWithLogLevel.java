/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

 /*
 * @test id=default
 * @summary Test Shenandoah with different log levels
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -Xms256M -Xmx1G -Xlog:gc*=error   TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -Xms256M -Xmx1G -Xlog:gc*=warning TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -Xms256M -Xmx1G -Xlog:gc*=info    TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -Xms256M -Xmx1G -Xlog:gc*=debug   TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -Xms256M -Xmx1G -Xlog:gc*=trace   TestWithLogLevel
 */

/*
 * @test id=generational
 * @summary Test Shenandoah with different log levels
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -Xms256M -Xmx1G -Xlog:gc*=error   TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -Xms256M -Xmx1G -Xlog:gc*=warning TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -Xms256M -Xmx1G -Xlog:gc*=info    TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -Xms256M -Xmx1G -Xlog:gc*=debug   TestWithLogLevel
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -Xms256M -Xmx1G -Xlog:gc*=trace   TestWithLogLevel
 */
import java.util.*;

public class TestWithLogLevel {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        long count = 300 * 1024 * 1024 / 16; // 300MB allocation
        for (long index = 0; index < count; index++) {
            Object sink = new Object();
            list.add(sink);
        }
    }
}
