/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 * @test
 * @bug 6528710
 * @summary Verifies sRGB-ColorSpace to sRGB-ColorSpace conversion quality
 */
public final class SimpleSRGBConversionQualityTest {

    public static void main(String[] args) {
        ColorSpace cspace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        float fvalue[] = {1.0f, 1.0f, 1.0f};

        Color c = new Color(cspace, fvalue, 1.0f);
        if (c.getRed() != 255 || c.getGreen() != 255 || c.getBlue() != 255) {
            throw new RuntimeException("Wrong color: " + c);
        }

        float frgbvalue[] = cspace.toRGB(fvalue);
        for (int i = 0; i < 3; ++i) {
            if (frgbvalue[i] != 1.0f) {
                System.err.println(fvalue[i] + " -> " + frgbvalue[i]);
                throw new RuntimeException("Wrong value");
            }
        }
    }
}
