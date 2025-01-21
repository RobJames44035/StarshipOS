/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.image.*;
import java.awt.geom.*;

/*
 * @test
 * @summary Check that BufferedImageFilter constructor and clone() method do not throw
 *          unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessBufferedImageFilter
 */

public class HeadlessBufferedImageFilter {
    public static void main(String args[]) {
        new BufferedImageFilter(new AffineTransformOp(new AffineTransform(), AffineTransformOp.TYPE_BILINEAR)).clone();
    }
}
