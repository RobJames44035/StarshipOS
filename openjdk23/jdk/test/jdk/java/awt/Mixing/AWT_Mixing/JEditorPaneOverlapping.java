/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import javax.swing.*;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JEditorPane } component.
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
 * @run main JEditorPaneOverlapping
 */
public class JEditorPaneOverlapping extends SimpleOverlappingTestBase {

    @Override
    protected JComponent getSwingComponent() {
        JEditorPane ch = new JEditorPane();
        ch.setText("<b>Swing component</b>");
        return ch;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JEditorPaneOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
