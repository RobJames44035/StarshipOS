/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import java.awt.Dimension;
import javax.swing.*;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JPanel } component in GlassPane.
 * <p>See base class for details.
 */
/*
 * @test
 * @key headful
 * @summary Simple Overlapping test for javax.swing.JPanel
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JPanelInGlassPaneOverlapping
 */
public class JPanelInGlassPaneOverlapping extends GlassPaneOverlappingTestBase {

    @Override
    protected JComponent getSwingComponent() {
        JPanel ch = new JPanel();
        ch.setPreferredSize(new Dimension(50, 50));
        ch.setBorder(BorderFactory.createTitledBorder("Swing Component"));
        return ch;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JPanelInGlassPaneOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
