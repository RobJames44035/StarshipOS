/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import java.awt.Dimension;
import javax.swing.*;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JTextArea } component in GlassPane.
 * <p>See base class for details.
 */
/*
 * @test
 * @key headful
 * @summary Simple Overlapping test for javax.swing.JLabel
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JTextAreaInGlassPaneOverlapping
 */
public class JTextAreaInGlassPaneOverlapping extends GlassPaneOverlappingTestBase {

    @Override
    protected JComponent getSwingComponent() {
        JTextArea ch = new JTextArea();
        ch.setPreferredSize(new Dimension(50, 50));
        ch.setText("Swing component");
        return ch;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JTextAreaInGlassPaneOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
