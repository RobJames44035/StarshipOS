/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8295407
 * @summary Superword should remove unsupported cmove packs from candidate packset
 * @library /test/lib /
 * @run main TestUnsupportedConditionalMove
 */

import jdk.test.lib.Asserts;

public class TestUnsupportedConditionalMove {
    public static int LENGTH = 3000;
    public static float[] fa = new float[LENGTH];
    public static float[] fb = new float[LENGTH];
    public static boolean[] mask = new boolean[LENGTH];

    public static void test() {
        for (int i = 0; i < fa.length; i++) {
            fb[i] = mask[i]? fa[i]: -fa[i];
        }
    }

    public static void main(String[] k) {
        for (int i= 0; i < LENGTH; i++) {
            mask[i] = (i % 3 == 0);
            fa[i] = i + 1.0f;
        }

        for (int i = 0; i < 10_000; i++) {
            test();
        }

        for (int i = 0; i < LENGTH; i++) {
            if (i % 3 == 0) {
                Asserts.assertEquals(fb[i], i + 1.0f);
            } else {
                Asserts.assertEquals(fb[i], - (i + 1.0f));
            }
        }
    }

}
