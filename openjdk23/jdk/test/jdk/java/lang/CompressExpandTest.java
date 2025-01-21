/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Test compress expand methods
 * @key randomness
 * @run testng/othervm -XX:+UnlockDiagnosticVMOptions -XX:DisableIntrinsic=_expand_i,_expand_l,_compress_i,_compress_l CompressExpandTest
 * @run testng CompressExpandTest
 */

public final class CompressExpandTest extends AbstractCompressExpandTest {
    @Override
    int actualCompress(int i, int mask) {
        return Integer.compress(i, mask);
    }

    @Override
    int actualExpand(int i, int mask) {
        return Integer.expand(i, mask);
    }

    @Override
    int expectedCompress(int i, int mask) {
        return testCompress(i, mask);
    }

    @Override
    int expectedExpand(int i, int mask) {
        return testExpand(i, mask);
    }


    @Override
    long actualCompress(long i, long mask) {
        return Long.compress(i, mask);
    }

    @Override
    long actualExpand(long i, long mask) {
        return Long.expand(i, mask);
    }

    @Override
    long expectedCompress(long i, long mask) {
        return testCompress(i, mask);
    }

    @Override
    long expectedExpand(long i, long mask) {
        return testExpand(i, mask);
    }
}
