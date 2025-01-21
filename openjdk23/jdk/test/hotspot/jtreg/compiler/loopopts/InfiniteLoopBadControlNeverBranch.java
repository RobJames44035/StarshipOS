/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8335709
 * @summary C2: assert(!loop->is_member(get_loop(useblock))) failed: must be outside loop
 * @library /test/lib
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,InfiniteLoopBadControlNeverBranch::* InfiniteLoopBadControlNeverBranch
 *
 */


import jdk.test.lib.Utils;

public class InfiniteLoopBadControlNeverBranch {
    static int b;
    static short c;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> test());
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(Utils.adjustTimeout(4000));
    }

    static void test() {
        int i = 0;
        while (true) {
            if (i > 1) {
                b = 0;
            }
            c = (short) (b * 7);
            i++;
        }
    }
}
