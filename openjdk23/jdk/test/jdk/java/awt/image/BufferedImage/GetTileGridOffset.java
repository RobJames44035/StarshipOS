/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/**
 * @test
 * @author Martin Desruisseaux
 */
public final class GetTileGridOffset {
    public static void main(final String[] args) {
        final BufferedImage image = new BufferedImage(
                20, 20, BufferedImage.TYPE_BYTE_GRAY);
        validate(image);

        final BufferedImage subImage = image.getSubimage(5, 5, 10, 10);
        verifyConsistency(subImage);
        validate(subImage);
    }

    /**
     * Verifies that the tile grid offsets of given image are consistent with
     * other properties (minimum pixel coordinates, minimum tile indices and
     * tile size). This method verifies the general contract, ignoring
     * simplifications brought by the {@link BufferedImage} specialization.
     * The purpose of this method is to demonstrate why tile grid offsets
     * need to be zero in the {@link BufferedImage} case.
     */
    private static void verifyConsistency(final RenderedImage image) {
        /*
         * This test will convert the coordinates of an arbitrary pixel in
         * the first tile to the indices of that tile. The expected result
         * is (minTileX, minTileY). We take the pixel in lower-right corner
         * of the first tile (the upper-left corner does not demonstrate
         * bug 8166038).
         */
        final int tileWidth  = image.getTileWidth();
        final int tileHeight = image.getTileHeight();
        final int x = image.getMinX() + tileWidth  - 1;
        final int y = image.getMinY() + tileHeight - 1;
        /*
         * From javax.media.jai.PlanarImage.XToTileX(int)
         * (omitting rounding toward negative infinity):
         *
         *  tileX = (x - tileGridXOffset) / tileWidth
         */
        int tileX = Math.floorDiv(x - image.getTileGridXOffset(), tileWidth);
        int tileY = Math.floorDiv(y - image.getTileGridYOffset(), tileHeight);
        int minTileX = image.getMinTileX();                 // Expected value
        int minTileY = image.getMinTileY();
        if (tileX != minTileX || tileY != minTileY) {
            throw new RuntimeException("Tile indices of upper-left tile"
                    + " shall be (" + minTileX + ", " + minTileY + ")."
                    + " But using the provided tileGridOffsets we got"
                    + " (" + tileX + ", " + tileY + ").");
        }
    }

    /**
     * Verifies that tile grid offsets are zero as required by
     * {@link BufferedImage} specification.
     */
    private static void validate(final BufferedImage image) {
        final int tileGridXOffset = image.getTileGridXOffset();
        final int tileGridYOffset = image.getTileGridYOffset();
        if (tileGridXOffset != 0) {
            throw new RuntimeException("BufferedImage.getTileGridXOffset()"
                    + " shall be zero. Got " + tileGridXOffset);
        }
        if (tileGridYOffset != 0) {
            throw new RuntimeException("BufferedImage.getTileGridTOffset()"
                    + " shall be zero. Got " + tileGridYOffset);
        }
    }
}
