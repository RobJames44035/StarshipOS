/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;

/**
 * @test
 * @bug 6211126 6211139
 */
public final class ExpectedNPEOnNull {

    public static void main(String[] args) {
        try {
            new ICC_ColorSpace(null);
            throw new RuntimeException("NPE is expected");
        } catch (NullPointerException ignored) {
            // expected
        }
        test(ICC_ColorSpace.getInstance(ColorSpace.CS_sRGB));
        test(ICC_ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB));
        test(ICC_ColorSpace.getInstance(ColorSpace.CS_CIEXYZ));
        test(ICC_ColorSpace.getInstance(ColorSpace.CS_PYCC));
        test(ICC_ColorSpace.getInstance(ColorSpace.CS_GRAY));
    }

    private static void test(ColorSpace cs) {
        try {
            cs.toRGB(null);
            throw new RuntimeException("NPE is expected");
        } catch (NullPointerException ignored) {
            // expected
        }
        try {
            cs.fromRGB(null);
            throw new RuntimeException("NPE is expected");
        } catch (NullPointerException ignored) {
            // expected
        }
        try {
            cs.toCIEXYZ(null);
            throw new RuntimeException("NPE is expected");
        } catch (NullPointerException ignored) {
            // expected
        }
        try {
            cs.fromCIEXYZ(null);
            throw new RuntimeException("NPE is expected");
        } catch (NullPointerException ignored) {
            // expected
        }
    }
}
