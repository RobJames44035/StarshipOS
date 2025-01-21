/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.java2d.metal;

import sun.font.GlyphList;
import sun.java2d.SunGraphics2D;
import sun.java2d.loops.GraphicsPrimitive;
import sun.java2d.pipe.BufferedTextPipe;
import sun.java2d.pipe.RenderQueue;

import java.awt.Composite;

class MTLTextRenderer extends BufferedTextPipe {

    MTLTextRenderer(RenderQueue rq) {
        super(rq);
    }

    @Override
    protected native void drawGlyphList(int numGlyphs, boolean usePositions,
                                        boolean subPixPos, boolean rgbOrder,
                                        int lcdContrast,
                                        float glOrigX, float glOrigY,
                                        long[] images, float[] positions);

    @Override
    protected void validateContext(SunGraphics2D sg2d, Composite comp) {
        // assert rq.lock.isHeldByCurrentThread();
        MTLSurfaceData mtlDst = (MTLSurfaceData)sg2d.surfaceData;
        MTLContext.validateContext(mtlDst, mtlDst,
                sg2d.getCompClip(), comp,
                null, sg2d.paint, sg2d,
                MTLContext.NO_CONTEXT_FLAGS);
    }

    MTLTextRenderer traceWrap() {
        return new Tracer(this);
    }

    private static class Tracer extends MTLTextRenderer {
        Tracer(MTLTextRenderer mtltr) {
            super(mtltr.rq);
        }
        protected void drawGlyphList(SunGraphics2D sg2d, GlyphList gl) {
            GraphicsPrimitive.tracePrimitive("MTLDrawGlyphs");
            super.drawGlyphList(sg2d, gl);
        }
    }
}
