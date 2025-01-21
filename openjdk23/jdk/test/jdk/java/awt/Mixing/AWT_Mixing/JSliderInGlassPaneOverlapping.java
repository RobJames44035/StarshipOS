/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import java.awt.Dimension;
import javax.swing.*;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JSlider } component in GlassPane.
 * <p>See base class for details.
 */
/*
 * @test
 * @key headful
 * @summary Simple Overlapping test for javax.swing.JSlider
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JSliderInGlassPaneOverlapping
 */
public class JSliderInGlassPaneOverlapping extends GlassPaneOverlappingTestBase {

    @Override
    protected JComponent getSwingComponent() {
        JSlider ch = new JSlider();
        ch.setPreferredSize(new Dimension(50, 50));
        return ch;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JSliderInGlassPaneOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
