/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package sun.font;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class NativeStrike extends PhysicalStrike {

    NativeFont nativeFont;

    NativeStrike(NativeFont nativeFont, FontStrikeDesc desc) {
        super(nativeFont, desc);

        throw new RuntimeException("NativeFont not used on OS X");
    }

    NativeStrike(NativeFont nativeFont, FontStrikeDesc desc,
                 boolean nocache) {
        super(nativeFont, desc);

        throw new RuntimeException("NativeFont not used on Windows");
    }


    void getGlyphImagePtrs(int[] glyphCodes, long[] images,int  len) {
    }

    long getGlyphImagePtr(int glyphCode) {
        return 0L;
    }

    long getGlyphImagePtrNoCache(int glyphCode) {
        return 0L;
    }

    void getGlyphImageBounds(int glyphcode,
                             Point2D.Float pt,
                             Rectangle result) {
    }

    Point2D.Float getGlyphMetrics(int glyphCode) {
        return null;
    }

    float getGlyphAdvance(int glyphCode) {
        return 0f;
    }

    Rectangle2D.Float getGlyphOutlineBounds(int glyphCode) {
        return null;
    }
    GeneralPath getGlyphOutline(int glyphCode, float x, float y) {
        return null;
    }

    GeneralPath getGlyphVectorOutline(int[] glyphs, float x, float y) {
        return null;
    }

}
