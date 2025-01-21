/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8341834 8343747
 * @summary Replicate node at a VectorCast (ConvL2I) causes superword to fail
 * @run main/othervm -XX:CompileCommand=compileonly,TestReplicateAtConv::test -Xcomp TestReplicateAtConv
 * @run main/othervm -XX:CompileCommand=compileonly,TestReplicateAtConv::test -Xcomp -XX:MaxVectorSize=8 TestReplicateAtConv
 */

public class TestReplicateAtConv {
    public static long val = 0;

    public static void test() {
        int array[] = new int[500];

        for (int i = 0; i < 100; i++) {
            for (long l = 100; l > i; l--) {
                val = 42 + (l + i);
                array[(int)l] = (int)l - (int)val;
            }
        }
    }

    public static void main(String[] args) {
        test();
    }
}
