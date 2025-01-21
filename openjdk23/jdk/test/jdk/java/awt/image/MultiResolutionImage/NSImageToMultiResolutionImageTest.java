/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MultiResolutionImage;

import jdk.test.lib.Platform;

/*
 * @test
 * @bug 8033534 8035069
 * @summary [macosx] Get MultiResolution image from native system
 * @author Alexander Scherbatiy
 * @modules java.desktop/sun.awt.image
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @run main NSImageToMultiResolutionImageTest
 */

public class NSImageToMultiResolutionImageTest {

    public static void main(String[] args) throws Exception {

        if (!Platform.isOSX()) {
            return;
        }

        String icon = "NSImage://NSApplicationIcon";
        final Image image = Toolkit.getDefaultToolkit().getImage(icon);

        if (!(image instanceof MultiResolutionImage)) {
            throw new RuntimeException("Icon does not have resolution variants!");
        }

        MultiResolutionImage multiResolutionImage = (MultiResolutionImage) image;

        int width = 0;
        int height = 0;

        for (Image resolutionVariant : multiResolutionImage.getResolutionVariants()) {
            int rvWidth = resolutionVariant.getWidth(null);
            int rvHeight = resolutionVariant.getHeight(null);
            if (rvWidth < width || rvHeight < height) {
                throw new RuntimeException("Resolution variants are not sorted!");
            }
            width = rvWidth;
            height = rvHeight;
        }
    }
}
