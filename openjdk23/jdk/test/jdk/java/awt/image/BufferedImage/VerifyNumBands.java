/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.image.BufferedImage;

/**
 * @test
 * @bug 8318850
 * @summary Checks the total number of bands of image data
 */
public final class VerifyNumBands {

    public static void main(String[] args) {
        test(BufferedImage.TYPE_INT_RGB, 3);
        test(BufferedImage.TYPE_INT_ARGB, 4);
        test(BufferedImage.TYPE_INT_ARGB_PRE, 4);
        test(BufferedImage.TYPE_INT_BGR, 3);
        test(BufferedImage.TYPE_3BYTE_BGR, 3);
        test(BufferedImage.TYPE_4BYTE_ABGR, 4);
        test(BufferedImage.TYPE_4BYTE_ABGR_PRE, 4);
        test(BufferedImage.TYPE_USHORT_565_RGB, 3);
        test(BufferedImage.TYPE_USHORT_555_RGB, 3);
        test(BufferedImage.TYPE_BYTE_GRAY, 1);
        test(BufferedImage.TYPE_USHORT_GRAY, 1);
    }

    private static void test(int type, int expected) {
        BufferedImage bi = new BufferedImage(1, 1, type);
        int numBands = bi.getRaster().getSampleModel().getNumBands();
        if (numBands != expected) {
            System.err.println("Expected: " + expected);
            System.err.println("Actual: " + numBands);
            throw new RuntimeException("wrong number of bands");
        }
    }
}
