/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.*;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JScrollBar } component.
 * <p>See base class for details.
 */
/*
 * @test
 * @key headful
 * @summary Simple Overlapping test for javax.swing.JScrollBar
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JScrollBarOverlapping
 */
public class JScrollBarOverlapping extends SimpleOverlappingTestBase {

    public JScrollBarOverlapping() {
        super(false);
    }

    @Override
    protected JComponent getSwingComponent() {
        JScrollBar ch = new JScrollBar(JScrollBar.VERTICAL);
        ch.setPreferredSize(new Dimension(50, 50));
        ch.setValue(50);
        ch.addAdjustmentListener(new AdjustmentListener() {

            public void adjustmentValueChanged(AdjustmentEvent e) {
                wasLWClicked = true;
            }
        });
        OverlappingTestBase.shift = new Point(20, 16);
        return ch;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JScrollBarOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
