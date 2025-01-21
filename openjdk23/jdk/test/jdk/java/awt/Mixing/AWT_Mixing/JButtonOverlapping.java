/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import javax.swing.*;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JButton } component.
 * <p>See base class for details.
 */
/*
 * @test
 * @key headful
 * @summary Simple Overlapping test for javax.swing.JButton
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JButtonOverlapping
 */
public class JButtonOverlapping extends SimpleOverlappingTestBase {

    @Override
    protected JComponent getSwingComponent() {
        JButton ch = new JButton();
        ch.setText("Swing component");
        return ch;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JButtonOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
