/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test id=default
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UseShenandoahGC -Xms128m -Xmx128m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC          -Xmx128m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -Xms128m          TestLargePages
 *
 * @run main/othervm -XX:+UseShenandoahGC -Xms131m -Xmx131m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC          -Xmx131m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -Xms131m          TestLargePages
 */

/*
 * @test id=lp
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages -Xms128m -Xmx128m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages          -Xmx128m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages -Xms128m          TestLargePages
 *
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages -Xms131m -Xmx131m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages          -Xmx131m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseLargePages -Xms131m          TestLargePages
 */

/*
 * @test id=thp
 * @requires vm.gc.Shenandoah
 * @requires os.family == "linux"
 *
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseTransparentHugePages -Xms128m -Xmx128m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseTransparentHugePages          -Xmx128m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseTransparentHugePages -Xms128m          TestLargePages
 *
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseTransparentHugePages -Xms131m -Xmx131m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseTransparentHugePages          -Xmx131m TestLargePages
 * @run main/othervm -XX:+UseShenandoahGC -XX:+UseTransparentHugePages -Xms131m          TestLargePages
 */

public class TestLargePages {
    public static void main(String[] args) {
        // Everything is checked on initialization
    }
}
