/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @key stress
 *
 * @summary converted from VM testbase nsk/stress/stack/stack019.
 * VM testbase keywords: [stress, diehard, stack, nonconcurrent]
 * VM testbase readme:
 * DESCRIPTION
 *     The test invokes infinitely recursive method from within stack
 *     overflow handler -- repeatedly multiple times in a single thread.
 *     The test is deemed passed, if VM have not crashed, and if exception
 *     other than due to stack overflow was not thrown.
 * COMMENTS
 *     This test crashes HS versions 2.0, 1.3, and 1.4 on both
 *     Solaris and Win32 platforms.
 *     See the bug:
 *     4366625 (P4/S4) multiple stack overflow causes HS crash
 *     The stack size is too small to run on systems with > 4K page size.
 *     Making it bigger could cause timeouts on other platform.
 *
 * @requires (vm.opt.DeoptimizeALot != true & vm.compMode != "Xcomp" & vm.pageSize == 4096)
 * @requires os.family != "windows"
 * @run main/othervm/timeout=900 -Xss200K Stack019
 */

public class Stack019 {
    private final static int CYCLES = 50;
    private final static int PROBES = 50;

    public static void main(String[] args) {
        //
        // Measure recursive depth before stack overflow:
        //
        try {
            recurse(0);
        } catch (StackOverflowError | OutOfMemoryError err) {
        }
        System.out.println("Maximal recursion depth: " + maxDepth);
        depthToTry = maxDepth;

        //
        // Run the tested threads:
        //
        for (int i = 0; i < CYCLES; i++) {
            try {
                System.out.println("Iteration: " + i + "/" + CYCLES);
                trickyRecurse(0);
                throw new RuntimeException("# TEST_BUG: stack overflow was expected!");
            } catch (StackOverflowError | OutOfMemoryError error) {
                // It's OK
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }

    private static int maxDepth;
    private static int depthToTry;

    private static void recurse(int depth) {
        maxDepth = depth;
        recurse(depth + 1);
    }

    private static void trickyRecurse(int depth) {
        try {
            maxDepth = depth;
            trickyRecurse(depth + 1);
        } catch (StackOverflowError | OutOfMemoryError error){
            //
            // Stack problem caught: provoke it again,
            // if current stack is enough deep:
            //
            if (depth < depthToTry - PROBES) {
                throw error;
            }
            recurse(depth + 1);
        }
    }
}
