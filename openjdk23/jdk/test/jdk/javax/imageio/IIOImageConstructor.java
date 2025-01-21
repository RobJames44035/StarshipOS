/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4392024
 * @summary Checks for IllegalArgumentException in IIOImage constructor
 */

import java.awt.image.BufferedImage;

import javax.imageio.IIOImage;

public class IIOImageConstructor {

    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(1, 1,
                                                BufferedImage.TYPE_INT_RGB);
        try {
            IIOImage iioi = new IIOImage(image, null, null);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException
                ("IIOImage constructor taking a RenderedImage fails!");
        }
    }
}
