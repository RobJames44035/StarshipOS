/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Check that Shenandoah's AlwaysPreTouch does not fire asserts
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+AlwaysPreTouch                                  -Xmx1g TestAlwaysPreTouch
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+AlwaysPreTouch -XX:ConcGCThreads=2              -Xmx1g TestAlwaysPreTouch
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+AlwaysPreTouch -XX:ParallelGCThreads=2          -Xmx1g TestAlwaysPreTouch
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+AlwaysPreTouch                         -Xms128m -Xmx1g TestAlwaysPreTouch
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+AlwaysPreTouch                           -Xms1g -Xmx1g TestAlwaysPreTouch
 */

public class TestAlwaysPreTouch {

    public static void main(String[] args) throws Exception {
        // checking the initialization before entering main()
    }

}
