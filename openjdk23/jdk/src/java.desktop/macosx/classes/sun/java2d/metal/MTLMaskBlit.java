/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.java2d.metal;

import sun.java2d.SurfaceData;
import sun.java2d.loops.CompositeType;
import sun.java2d.loops.GraphicsPrimitive;
import sun.java2d.loops.GraphicsPrimitiveMgr;
import sun.java2d.loops.SurfaceType;
import sun.java2d.pipe.BufferedMaskBlit;
import sun.java2d.pipe.Region;

import java.awt.Composite;

import static sun.java2d.loops.CompositeType.SrcNoEa;
import static sun.java2d.loops.CompositeType.SrcOver;
import static sun.java2d.loops.SurfaceType.*;

class MTLMaskBlit extends BufferedMaskBlit {

    static void register() {
        GraphicsPrimitive[] primitives = {
                new MTLMaskBlit(IntArgb,    SrcOver),
                new MTLMaskBlit(IntArgbPre, SrcOver),
                new MTLMaskBlit(IntRgb,     SrcOver),
                new MTLMaskBlit(IntRgb,     SrcNoEa),
                new MTLMaskBlit(IntBgr,     SrcOver),
                new MTLMaskBlit(IntBgr,     SrcNoEa),
        };
        GraphicsPrimitiveMgr.register(primitives);
    }

    private MTLMaskBlit(SurfaceType srcType,
                        CompositeType compType)
    {
        super(MTLRenderQueue.getInstance(),
                srcType, compType, MTLSurfaceData.MTLSurface);
    }

    @Override
    protected void validateContext(SurfaceData dstData,
                                   Composite comp, Region clip)
    {
        MTLSurfaceData mtlDst = (MTLSurfaceData)dstData;
        MTLContext.validateContext(mtlDst, mtlDst,
                clip, comp, null, null, null,
                MTLContext.NO_CONTEXT_FLAGS);
    }
}
