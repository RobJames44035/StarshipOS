/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8307927
 * @summary C2: "malformed control flow" with irreducible loop
 * @compile MalformedControlIrreducibleLoop.jasm
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:CompileOnly=TestMalformedControlIrreducibleLoop::test TestMalformedControlIrreducibleLoop
 */

public class TestMalformedControlIrreducibleLoop {
    public static void main(String[] args) {
        new MalformedControlIrreducibleLoop();
        test(false);
    }

    private static void test(boolean flag) {
        int i;
        for (i = 1; i < 2; i *= 2) {
        }
        if (flag) {
            MalformedControlIrreducibleLoop.actualTest(i);
        }
    }
}
