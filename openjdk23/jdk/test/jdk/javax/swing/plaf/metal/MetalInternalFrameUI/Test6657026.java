/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6657026
 * @summary Tests shared MetalInternalFrameUI in different application contexts
 * @author Sergey Malenkov
 * @modules java.desktop/sun.awt
 */

import sun.awt.SunToolkit;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalInternalFrameUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Test6657026 extends MetalInternalFrameUI implements Runnable {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new MetalLookAndFeel());

        ThreadGroup group = new ThreadGroup("$$$");
        Thread thread = new Thread(group, new Test6657026());
        thread.start();
        thread.join();

        new JInternalFrame().setContentPane(new JPanel());
    }

    public Test6657026() {
        super(null);
    }

    public void run() {
        SunToolkit.createNewAppContext();
        IS_PALETTE = JInternalFrame.CONTENT_PANE_PROPERTY;
    }
}
