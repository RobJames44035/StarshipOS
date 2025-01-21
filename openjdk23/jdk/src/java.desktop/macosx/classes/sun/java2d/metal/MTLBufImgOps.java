/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.java2d.metal;

import sun.java2d.SunGraphics2D;
import sun.java2d.SurfaceData;
import sun.java2d.loops.CompositeType;
import sun.java2d.pipe.BufferedBufImgOps;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;

import static sun.java2d.metal.MTLContext.MTLContextCaps.CAPS_EXT_BIOP_SHADER;

class MTLBufImgOps extends BufferedBufImgOps {

    /**
     * This method is called from MTLDrawImage.transformImage() only.  It
     * validates the provided BufferedImageOp to determine whether the op
     * is one that can be accelerated by the MTL pipeline.  If the operation
     * cannot be completed for any reason, this method returns false;
     * otherwise, the given BufferedImage is rendered to the destination
     * using the provided BufferedImageOp and this method returns true.
     */
    static boolean renderImageWithOp(SunGraphics2D sg, BufferedImage img,
                                     BufferedImageOp biop, int x, int y)
    {
        // Validate the provided BufferedImage (make sure it is one that
        // is supported, and that its properties are acceleratable)
        if (biop instanceof ConvolveOp) {
            if (!isConvolveOpValid((ConvolveOp)biop)) {
                return false;
            }
        } else if (biop instanceof RescaleOp) {
            if (!isRescaleOpValid((RescaleOp)biop, img)) {
                return false;
            }
        } else if (biop instanceof LookupOp) {
            if (!isLookupOpValid((LookupOp)biop, img)) {
                return false;
            }
        } else {
            // No acceleration for other BufferedImageOps (yet)
            return false;
        }

        SurfaceData dstData = sg.surfaceData;
        if (!(dstData instanceof MTLSurfaceData) ||
                (sg.interpolationType == AffineTransformOp.TYPE_BICUBIC) ||
                (sg.compositeState > SunGraphics2D.COMP_ALPHA))
        {
            return false;
        }

        SurfaceData srcData =
                dstData.getSourceSurfaceData(img, SunGraphics2D.TRANSFORM_ISIDENT,
                        CompositeType.SrcOver, null);
        if (!(srcData instanceof MTLSurfaceData)) {
            // REMIND: this hack tries to ensure that we have a cached texture
            srcData =
                    dstData.getSourceSurfaceData(img, SunGraphics2D.TRANSFORM_ISIDENT,
                            CompositeType.SrcOver, null);
            if (!(srcData instanceof MTLSurfaceData)) {
                return false;
            }
        }

        // Verify that the source surface is actually a texture and
        // that the operation is supported
        MTLSurfaceData mtlSrc = (MTLSurfaceData)srcData;
        MTLGraphicsConfig gc = mtlSrc.getMTLGraphicsConfig();
        if (mtlSrc.getType() != MTLSurfaceData.TEXTURE ||
                !gc.isCapPresent(CAPS_EXT_BIOP_SHADER))
        {
            return false;
        }

        int sw = img.getWidth();
        int sh = img.getHeight();
        MTLBlitLoops.IsoBlit(srcData, dstData,
                img, biop,
                sg.composite, sg.getCompClip(),
                sg.transform, sg.interpolationType,
                0, 0, sw, sh,
                x, y, x+sw, y+sh,
                true);

        return true;
    }
}
