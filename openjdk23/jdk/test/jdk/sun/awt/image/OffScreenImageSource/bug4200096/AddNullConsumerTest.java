/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.awt.image.BufferedImage;

/**
 * @test
 * @bug 4200096
 * @summary OffScreenImageSource.addConsumer(null) shouldn't throw (or print) a NullPointerException
 * @author Jeremy Wood
 */

/**
 * This makes sure if OffScreenImageSource#addConsumer(null) is called: we
 * treat that as a no-op and return immediately.
 * <p>
 * This test exists primarily to make sure the resolution to 4200096 does not
 * significantly change legacy behavior. Whether or not a NPE is printed to
 * System.err is not a hotly contested question, but at one point one of the
 * proposed (and rejected) resolutions to 4200096 had the potential to
 * throw a NPE when addConsumer(null) was called. That would be a
 * significant change that we want to avoid.
 * </p>
 */
 public class AddNullConsumerTest {
    public static void main(String[] args) throws Exception {
        try (AutoCloseable setup = bug4200096.setupTest(false)) {
            BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            bufferedImage.getSource().addConsumer(null);
        }
    }
}