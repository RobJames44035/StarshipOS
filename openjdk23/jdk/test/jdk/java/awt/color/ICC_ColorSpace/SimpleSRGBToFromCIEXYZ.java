/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.awt.color.ColorSpace;
import java.util.Arrays;

/**
 * @test
 * @bug 4760025
 * @summary Verifies sRGB conversions to and from CIE XYZ
 */
public final class SimpleSRGBToFromCIEXYZ {

    public static void main(String[] args) {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        for (float g : new float[]{1.0f, 0.8f, 0.6f}) {
            float[] rgb = {0, g, 0};
            float[] xyz = cs.toCIEXYZ(rgb);
            float[] inv = cs.fromCIEXYZ(xyz);

            if (inv[0] != 0 || Math.abs(inv[1] - g) > 0.0001f || inv[2] != 0) {
                System.err.println("Expected color:\t" + Arrays.toString(rgb));
                System.err.println("XYZ color:\t\t" + Arrays.toString(xyz));
                System.err.println("Actual color:\t" + Arrays.toString(inv));
                throw new Error("Wrong color");
            }
        }
    }
}
