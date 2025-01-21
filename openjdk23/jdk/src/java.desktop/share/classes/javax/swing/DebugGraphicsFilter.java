/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package javax.swing;

import java.awt.*;
import java.awt.image.*;

/** Color filter for DebugGraphics, used for images only.
  *
  * @author Dave Karlton
  */
class DebugGraphicsFilter extends RGBImageFilter {
    Color color;

    DebugGraphicsFilter(Color c) {
        canFilterIndexColorModel = true;
        color = c;
    }

    public int filterRGB(int x, int y, int rgb) {
        return color.getRGB() | (rgb & 0xFF000000);
    }
}
