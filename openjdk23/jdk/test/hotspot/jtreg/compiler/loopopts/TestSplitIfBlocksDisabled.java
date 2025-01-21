/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test TestSplitIfBlocksDisabled
 * @bug 8086057
 * @summary Verifies that loop optimizations work if SplitIfBlocks is disabled.
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -Xcomp -XX:-SplitIfBlocks
 *                   compiler.loopopts.TestSplitIfBlocksDisabled
 */

package compiler.loopopts;

public class TestSplitIfBlocksDisabled {

    public static void main(String[] args) {
      System.out.println("Passed");
    }
}

