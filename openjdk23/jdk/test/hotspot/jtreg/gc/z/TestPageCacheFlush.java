/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc.z;

/*
 * @test TestPageCacheFlush
 * @requires vm.gc.Z
 * @summary Test ZGC page cache flushing
 * @library /test/lib
 * @run driver gc.z.TestPageCacheFlush
 */

import java.util.LinkedList;
import jdk.test.lib.process.ProcessTools;

public class TestPageCacheFlush {
    static class Test {
        private static final int K = 1024;
        private static final int M = K * K;
        private static volatile LinkedList<byte[]> keepAlive;

        public static void fillPageCache(int size) {
            System.out.println("Begin allocate (" + size + ")");

            keepAlive = new LinkedList<>();

            try {
                for (;;) {
                    keepAlive.add(new byte[size]);
                }
            } catch (OutOfMemoryError e) {
                keepAlive = null;
                System.gc();
            }

            System.out.println("End allocate (" + size + ")");
        }

        public static void main(String[] args) throws Exception {
            // Allocate small objects to fill the page cache with small pages
            fillPageCache(10 * K);

            // Allocate large objects to provoke page cache flushing to rebuild
            // cached small pages into large pages
            fillPageCache(10 * M);
        }
    }

    public static void main(String[] args) throws Exception {
        ProcessTools.executeTestJava(
            "-XX:+UseZGC",
            "-Xms128M",
            "-Xmx128M",
            "-Xlog:gc,gc+init,gc+heap=debug",
            Test.class.getName())
                .outputTo(System.out)
                .errorTo(System.out)
                .shouldContain("Page Cache Flushed:")
                .shouldHaveExitValue(0);
    }
}
