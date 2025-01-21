/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestEnoughUnusedSpace
 * @requires vm.gc.Epsilon
 * @summary Epsilon should allocates object successfully if it has enough space.
 * @run main/othervm -Xms64M -Xmx128M -XX:+UnlockExperimentalVMOptions
 *                   -XX:+UseEpsilonGC gc.epsilon.TestEnoughUnusedSpace
 */

public class TestEnoughUnusedSpace {
    static volatile Object arr;

    public static void main(String[] args) {
        // Create an array about 90M. It should be created successfully
        // instead of throwing OOME, because 90M is smaller than 128M.
        arr = new byte[90 * 1024 * 1024];
    }
}
