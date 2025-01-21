/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4091185
 * @summary Repeatedly OR BitSets; No OutOfMemoryException should result
 */

import java.util.*;

/**
 * This is a simple test class that repeatedly ORs two
 * BitSets of unequal size together. Previously this
 * caused an exponential growth in the memory underlying
 * the BitSets quickly using all available memory
 */
public class MemoryLeak {

   public static void main(String[] args) {

        //create 2 test bitsets
        BitSet setOne = new BitSet();
        BitSet setTwo = new BitSet();

        setOne.set(64);
        setTwo.set(129);

        //test for bug #4091185
        //exponential set growth causing memory depletion
        for (int i = 0; i < 50; i++) {
            setOne.or(setTwo);
            setTwo.or(setOne);
        }
    }
}
