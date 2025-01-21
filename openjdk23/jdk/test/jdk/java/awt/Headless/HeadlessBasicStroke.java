/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that BasicStroke constructors and get-methods do not
 *          throw exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessBasicStroke
 */


public class HeadlessBasicStroke {
    public static void main (String[] args) {
        BasicStroke bs;

        // Constructors without exceptions
        bs = new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 3, null, -1);
        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 3,
                    new float[]{(float) 2.0, (float) 3.0}, 0);
        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 3,
                    new float[]{(float) 2.0, (float) 3.0}, 1);
        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, (float) 3);

        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, (float) 3);

        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, (float) 3);

        bs = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 3);

        bs = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 3);

        bs = new BasicStroke(1, BasicStroke.JOIN_ROUND, BasicStroke.CAP_SQUARE, 3);

        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);

        bs = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        bs = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);

        bs = new BasicStroke(1, BasicStroke.JOIN_ROUND, BasicStroke.CAP_SQUARE);

        bs = new BasicStroke((float) 0.1);

        bs = new BasicStroke((float) 0.9);

        bs = new BasicStroke(4);

        bs = new BasicStroke(10);

        bs = new BasicStroke(20);

        bs = new BasicStroke(100);

        bs = new BasicStroke();

        // Constructors with exceptions
        boolean exceptions = false;
        try {
            bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, (float) 0.2);
        } catch (IllegalArgumentException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw IllegalArgumentException when expected");

        exceptions = false;
        try {
            bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, (float) 0.2);
        } catch (IllegalArgumentException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw IllegalArgumentException when expected");

        exceptions = false;
        try {
            bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, (float) 0.2);
        } catch (IllegalArgumentException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw IllegalArgumentException when expected");

        exceptions = false;
        try {
            bs = new BasicStroke(1, 5678, 92039, 3);
        } catch (IllegalArgumentException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw IllegalArgumentException when expected");

        exceptions = false;
        try {
            bs = new BasicStroke(1, 5678, 92039);
        } catch (IllegalArgumentException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw IllegalArgumentException when expected");

        exceptions = false;
        try {
            bs = new BasicStroke((float) -0.9);
        } catch (IllegalArgumentException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw IllegalArgumentException when expected");

        // Create stroke shape
        bs = new BasicStroke(20);
        bs.createStrokedShape(new Rectangle(10, 10, 10, 10));

        // Get-methods
        bs = new BasicStroke(100);
        bs.getLineWidth();
        bs.getEndCap();
        bs.getLineJoin();
        bs.getMiterLimit();
        bs.getDashArray();
        bs.getDashPhase();
    }
}
