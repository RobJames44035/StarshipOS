/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.color.ICC_ProfileRGB;

/**
 * @test
 * @bug 4267139
 * @summary This test verifies that an sRGB color can be accurately converted to
 *          an CIE colorimetric XYZ value.
 */
public final class XYZTest {

    public static void main(String[] args) {
        float[] rgb = new float[3];
        rgb[0] = 1.0F;
        rgb[1] = 1.0F;
        rgb[2] = 1.0F;
        ICC_ProfileRGB pf =
            (ICC_ProfileRGB) ICC_Profile.getInstance(ColorSpace.CS_sRGB);
        ICC_ColorSpace srgb = new ICC_ColorSpace(pf);
        float[] xyz = srgb.toCIEXYZ(rgb);
        float mxyz[] = new float[3];
        // adjust D50 relative values by a factor which is the ratio
        // of the device white point (in this case sRGB) divided by
        // the D50 (PCS) white point
        mxyz[0] = xyz[0] * (0.9505f / 0.9642f);
        mxyz[1] = xyz[1] * (1.0000f / 1.0000f);
        mxyz[2] = xyz[2] * (1.0891f / 0.8249f);
        if ((Math.abs(mxyz[0] - 0.9505f) > 0.01f) ||
            (Math.abs(mxyz[1] - 1.0000f) > 0.01f) ||
            (Math.abs(mxyz[2] - 1.0891f) > 0.01f)) {
            throw new Error("sRGB (1.0, 1.0, 1.0) doesn't convert " +
                            "correctly to CIEXYZ");
        }
    }
}
