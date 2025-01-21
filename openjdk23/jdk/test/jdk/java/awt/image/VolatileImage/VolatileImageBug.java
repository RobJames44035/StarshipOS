/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.VolatileImage;

/**
 * @test
 * @key headful
 * @bug 8140530
 * @run main VolatileImageBug
 * @summary Creating volatileimage(0,0) should throw IAE
 */
public class VolatileImageBug {
    public static void main(String[] args) {

        boolean iaeThrown = false;
        GraphicsEnvironment ge = GraphicsEnvironment.
                                      getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().
                                           getDefaultConfiguration();
        try {
            VolatileImage volatileImage = gc.createCompatibleVolatileImage(0, 0);
        } catch (IllegalArgumentException iae) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new RuntimeException ("IllegalArgumentException not thrown " +
                                        "for createCompatibleVolatileImage(0,0)");
        }
    }
}

