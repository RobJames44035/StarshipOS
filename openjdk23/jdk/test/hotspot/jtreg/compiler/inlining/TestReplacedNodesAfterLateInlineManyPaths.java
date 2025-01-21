/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * bug 8312980
 * @summary C2: "malformed control flow" created during incremental inlining
 * @requires vm.compiler2.enabled
 * @run main/othervm  -XX:CompileCommand=compileonly,TestReplacedNodesAfterLateInlineManyPaths::* -XX:-BackgroundCompilation
 *                    -XX:+IgnoreUnrecognizedVMOptions -XX:+AlwaysIncrementalInline TestReplacedNodesAfterLateInlineManyPaths
 */

public class TestReplacedNodesAfterLateInlineManyPaths {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test("" + i);
        }
    }

    public static int test(String s) {
        int result = 0;
        int len = s.length();
        int i = 0;
        while (i < len) {
            // charAt is inlined late, and i is constrained by CastII(i >= 0)
            // The constraint comes from intrinsic checkIndex
            s.charAt(i);
            // Graph below intentionally branches out 4x, and merges again (4-fold diamonds).
            // This creates an exponential explosion in number of paths.
            int e = i;
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            // Comment out lines below to make it not assert
            // assert(C->live_nodes() <= C->max_node_limit()) failed: Live Node limit exceeded limit
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            e = (e & 7) + (e & 31) + (e & 1111) + (e & 1000_000);
            result += e;
            i++;
        }
        return result;
    }
}
