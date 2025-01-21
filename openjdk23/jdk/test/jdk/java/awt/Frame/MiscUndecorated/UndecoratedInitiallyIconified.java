/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.FlowLayout;
/*
 * @test
 * @key headful
 * @bug 4464710 7102299
 * @summary Recurring bug is, an undecorated JFrame cannot be set iconified
 *          before setVisible(true)
 *
 * @run main UndecoratedInitiallyIconified
 */


public class UndecoratedInitiallyIconified {
    private static JFrame frame;
    public static void main(String args[]) throws Exception {
        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.ICONIFIED)) {
            return;
        }
        EventQueue.invokeAndWait( () -> {
            frame = new JFrame("Test Frame");
            frame.setLayout(new FlowLayout());
            frame.setBounds(50,50,300,300);
            frame.setUndecorated(true);
            frame.setExtendedState(Frame.ICONIFIED);
            if(frame.getExtendedState() != Frame.ICONIFIED) {
                throw new RuntimeException("getExtendedState is not Frame.ICONIFIED as expected");
            }
        });
    }
}
