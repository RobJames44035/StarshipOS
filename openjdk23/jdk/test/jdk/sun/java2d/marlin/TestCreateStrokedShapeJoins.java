/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.Arrays;
import javax.imageio.*;

/**
 * @test
 * @bug 8316741
 * @summary Verifies that Marlin renderer's Stroker generates properly joins
 * in createStrokedShape()
 * @run main TestCreateStrokedShapeJoins
 */
public class TestCreateStrokedShapeJoins {

    static final boolean SAVE_IMAGE = false;

    private final static int W = 200;

    private final static int[] REF_COUNTS = new int[] {4561, 4790, 5499};

    public static void main(String[] args) throws Exception {
        final int[] test = new int[] {
                test(BasicStroke.JOIN_BEVEL),
                test(BasicStroke.JOIN_ROUND),
                test(BasicStroke.JOIN_MITER)
        };

        System.out.println("test: " + Arrays.toString(test));
        System.out.println("ref:  " + Arrays.toString(REF_COUNTS));

        // check results:
        for (int i = 0; i < REF_COUNTS.length; i++) {
            if (test[i] != REF_COUNTS[i]) {
                throw new RuntimeException("Invalid test[" + i + "]: " + test[i] + " != " + REF_COUNTS[i]);
            }
        }
    }

    private static int test(int join) throws Exception {
        final BufferedImage image = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        try {
            g.setPaint(Color.BLACK);
            g.fillRect(0, 0, W, W);
            g.setPaint(Color.WHITE);
            g.setTransform(new AffineTransform(W, 0, 0, W, 0, 0));

            final BasicStroke stroke = new BasicStroke(0.15f, 0, join, 10);

            final Path2D p = new Path2D.Float();
            p.moveTo(0.95f, 0.6f);
            p.lineTo(0.5f, 0.5f);
            p.lineTo(0.95f, 0.4f);

            final Shape outline = stroke.createStrokedShape(p);
            g.fill(outline);
        } finally {
            g.dispose();
        }
        if (SAVE_IMAGE) {
            final File file = new File("TestCreateStrokedShapeJoins-" + join + ".png");
            System.out.println("Writing " + file.getAbsolutePath());
            ImageIO.write(image, "png", file);
        }
        int count = 0;

        for (int y = 0; y < W; y++) {
            for (int x = 0; x < W; x++) {
                final int rgb = image.getRGB(x, y);
                final int b = rgb & 0xFF;

                if (b != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
