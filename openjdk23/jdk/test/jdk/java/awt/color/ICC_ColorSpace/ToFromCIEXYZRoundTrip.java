/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.awt.color.ColorSpace;
import java.util.Arrays;

/**
 * @test
 * @bug 8288633
 * @run main/othervm/timeout=1000 ToFromCIEXYZRoundTrip
 * @summary Verifies precision of ICC_ColorSpace.toCIEXYZ/fromCIEXYZ
 */
public final class ToFromCIEXYZRoundTrip {

    private static volatile boolean failed;

    public static void main(String[] args) throws InterruptedException {
        int[] css = {
            ColorSpace.CS_CIEXYZ, ColorSpace.CS_GRAY, ColorSpace.CS_LINEAR_RGB,
            ColorSpace.CS_PYCC, ColorSpace.CS_sRGB
        };
        Thread[] threads = new Thread[css.length];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                test(ColorSpace.getInstance(css[finalI]));
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        if (failed) {
            throw new RuntimeException("Too many errors");
        }
    }

    private static void test(ColorSpace cs) {
        ColorSpace rgb = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        float[] color = new float[3];
        int equal = 0;
        int all = 0;
        for (int r = 0; r < 256; ++r) {
            for (int g = 0; g < 256; ++g) {
                for (int b = 0; b < 256; ++b) {
                    all++;
                    color[0] = r / 255.0f;
                    color[1] = g / 255.0f;
                    color[2] = b / 255.0f;
                    // Convert some values from srgb to ciexyz. We assume the
                    // result will be a "good" point in the ciexyz color space.
                    float[] good = rgb.toCIEXYZ(color);
                    // Round trip the "good" point, assuming that some results
                    // will be equal to the "good" point.
                    // If toCIEXYZ and fromCIEXYZ use different rendering
                    // intents then equal results are unlikely.
                    float[] rel = cs.toCIEXYZ(cs.fromCIEXYZ(good));
                    if (Arrays.equals(good, rel)) {
                        equal++;
                    }
                }
            }
        }
        int percent = (int) (equal / (all / 100.0f));
        System.err.println("All = " + all);
        System.err.println("Equal = " + equal);
        System.err.println("Percent = " + percent);
        if (equal < 100) {
            // the number 100 is based on the experiments, it was 0 before fix
            failed = true;
        }
    }
}
