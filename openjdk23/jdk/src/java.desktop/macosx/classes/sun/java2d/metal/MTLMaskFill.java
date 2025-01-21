/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package sun.java2d.metal;

import sun.java2d.SunGraphics2D;
import sun.java2d.SurfaceData;
import sun.java2d.loops.CompositeType;
import sun.java2d.loops.GraphicsPrimitive;
import sun.java2d.loops.GraphicsPrimitiveMgr;
import sun.java2d.loops.SurfaceType;
import sun.java2d.pipe.BufferedMaskFill;

import java.awt.Composite;

import static sun.java2d.loops.CompositeType.SrcNoEa;
import static sun.java2d.loops.CompositeType.SrcOver;
import static sun.java2d.loops.SurfaceType.*;

class MTLMaskFill extends BufferedMaskFill {

    static void register() {
        GraphicsPrimitive[] primitives = {
                new MTLMaskFill(AnyColor,                  SrcOver),
                new MTLMaskFill(OpaqueColor,               SrcNoEa),
                new MTLMaskFill(GradientPaint,             SrcOver),
                new MTLMaskFill(OpaqueGradientPaint,       SrcNoEa),
                new MTLMaskFill(LinearGradientPaint,       SrcOver),
                new MTLMaskFill(OpaqueLinearGradientPaint, SrcNoEa),
                new MTLMaskFill(RadialGradientPaint,       SrcOver),
                new MTLMaskFill(OpaqueRadialGradientPaint, SrcNoEa),
                new MTLMaskFill(TexturePaint,              SrcOver),
                new MTLMaskFill(OpaqueTexturePaint,        SrcNoEa),
        };
        GraphicsPrimitiveMgr.register(primitives);
    }

    protected MTLMaskFill(SurfaceType srcType, CompositeType compType) {
        super(MTLRenderQueue.getInstance(),
                srcType, compType, MTLSurfaceData.MTLSurface);
    }

    @Override
    protected native void maskFill(int x, int y, int w, int h,
                                   int maskoff, int maskscan, int masklen,
                                   byte[] mask);

    @Override
    protected void validateContext(SunGraphics2D sg2d,
                                   Composite comp, int ctxflags)
    {
        MTLSurfaceData dstData = SurfaceData.convertTo(MTLSurfaceData.class,
                                                       sg2d.surfaceData);

        MTLContext.validateContext(dstData, dstData,
                sg2d.getCompClip(), comp,
                null, sg2d.paint, sg2d, ctxflags);
    }
}
