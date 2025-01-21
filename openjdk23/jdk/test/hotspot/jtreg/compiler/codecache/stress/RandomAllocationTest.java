/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test RandomAllocationTest
 * @key stress randomness
 * @summary stressing code cache by allocating randomly sized "dummy" code blobs
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox compiler.codecache.stress.Helper compiler.codecache.stress.TestCaseImpl
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:-SegmentedCodeCache
 *                   compiler.codecache.stress.RandomAllocationTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:+SegmentedCodeCache
 *                   compiler.codecache.stress.RandomAllocationTest
 */

package compiler.codecache.stress;

import jdk.test.whitebox.code.BlobType;

import java.util.ArrayList;
import java.util.Random;
import jdk.test.lib.Utils;

public class RandomAllocationTest implements Runnable {
    private static final long CODE_CACHE_SIZE
            = Helper.WHITE_BOX.getUintxVMFlag("ReservedCodeCacheSize");
    private static final int MAX_BLOB_SIZE = (int) (CODE_CACHE_SIZE >> 7);
    private static final BlobType[] BLOB_TYPES
            = BlobType.getAvailable().toArray(new BlobType[0]);
    private final Random rng = Utils.getRandomInstance();

    public static void main(String[] args) {
        new CodeCacheStressRunner(new RandomAllocationTest()).runTest();
    }

    private final ArrayList<Long> blobs = new ArrayList<>();
    @Override
    public void run() {
        boolean allocate = blobs.isEmpty() || rng.nextBoolean();
        if (allocate) {
            int type = rng.nextInt(BLOB_TYPES.length);
            long addr = Helper.WHITE_BOX.allocateCodeBlob(
                    rng.nextInt(MAX_BLOB_SIZE), BLOB_TYPES[type].id);
            if (addr != 0) {
                blobs.add(addr);
            }
        } else {
            int index = rng.nextInt(blobs.size());
            Helper.WHITE_BOX.freeCodeBlob(blobs.remove(index));
        }
    }

}
