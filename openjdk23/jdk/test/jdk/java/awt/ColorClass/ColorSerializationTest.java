/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @bug 4330102
  @summary Tests that Color object is serializable
  @run main ColorSerializationTest
*/

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.IndexColorModel;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

public class ColorSerializationTest {

    public static void main(String[] args) {
        java.awt.Color cobj = new java.awt.Color(255, 255, 255);
        try {
            cobj.createContext(
                    new IndexColorModel(
                            8, 1,
                            new byte[]{0}, new byte[]{0}, new byte[]{0}),
                    new Rectangle(1, 1, 2, 3),
                    new Rectangle(3, 3),
                    new AffineTransform(),
                    new RenderingHints(null));
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ObjectOutputStream objos = new ObjectOutputStream(ostream);
            objos.writeObject(cobj);
            objos.close();
            System.out.println("Test PASSED");
        } catch (java.io.IOException e) {
            System.out.println("Test FAILED");
            throw new RuntimeException("Test FAILED: Color is not serializable: " + e.getMessage());
        }

    }
}
