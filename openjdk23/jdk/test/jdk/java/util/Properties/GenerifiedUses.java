/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 5060820 5054615 5056707 5061476
 * @modules java.desktop
 *          java.naming
 * @compile GenerifiedUses.java
 */

import java.awt.image.CropImageFilter;
import java.awt.image.ImageFilter;
import java.awt.image.PixelGrabber;
import java.awt.image.ReplicateScaleFilter;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.spi.NamingManager;

public class GenerifiedUses {

    static void foo() throws Exception {

        Properties props = new Properties();

        // 5060820
        new InitialDirContext(props);

        // 5054615
        new InitialContext(props);

        // 5056707
        NamingManager.getObjectInstance(null, null, null, props);

        // 5061476
        new CropImageFilter(0, 0, 0, 0).setProperties(props);
        new ImageFilter().setProperties(props);
        new PixelGrabber(null, 0, 0, 0, 0, false).setProperties(props);
        new ReplicateScaleFilter(1, 1).setProperties(props);

    }

}
