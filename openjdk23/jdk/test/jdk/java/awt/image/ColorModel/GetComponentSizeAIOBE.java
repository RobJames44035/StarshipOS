/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.image.ColorModel;

/**
 * @test
 * @bug 4677581
 * @summary checks when the ColorModel#getComponentSize() throws AIOBE
 */
public final class GetComponentSizeAIOBE {

    public static void main(String[] args) {
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < cm.getNumComponents(); ++i) {
            cm.getComponentSize(i);
        }

        testAIOBE(cm, Integer.MIN_VALUE);
        testAIOBE(cm, -1);
        testAIOBE(cm, cm.getNumComponents());
        testAIOBE(cm, cm.getNumComponents() + 1);
        testAIOBE(cm, Integer.MAX_VALUE);
    }

    private static void testAIOBE(ColorModel cm, int componentIdx) {
        try {
            cm.getComponentSize(componentIdx);
            throw new RuntimeException("AIOBE is not thrown");
        } catch (ArrayIndexOutOfBoundsException ignore) {
            // expected
        }
    }
}
