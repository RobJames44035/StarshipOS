/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import com.sun.java.swing.plaf.motif.MotifInternalFrameTitlePane;
import javax.swing.JInternalFrame;

/*
 * @test
 * @bug 4150591
 * @summary MotifInternalFrameTitlePane is public now and can be
 * instantiated by other classes within the desktop module without using Reflection.
 * This does not mean that this class will ever become part
 * of the official public Java API.
 * @modules java.desktop/com.sun.java.swing.plaf.motif
 * @run main bug4150591
 */

public class bug4150591 {
    public static void main(String[] args) {
        MotifInternalFrameTitlePane mtp = new MotifInternalFrameTitlePane(new JInternalFrame());
    }
}
