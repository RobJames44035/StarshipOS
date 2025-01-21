/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6726866 8186617 8343123
 * @summary Repainting artifacts when resizing or dragging JInternalFrames in
            non-opaque toplevel
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual bug6726866
*/

import java.awt.Color;
import java.awt.Window;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class bug6726866 {

    private static final String INSTRUCTIONS = """
            Drag the internal frame inside the green undecorated window,
            if you can drag it the test passes, otherwise fails. """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("JInternalFrame Instructions")
                .instructions(INSTRUCTIONS)
                .rows(5)
                .columns(35)
                .testUI(bug6726866::createUI)
                .build()
                .awaitAndCheck();
    }

    private static JFrame createUI() {
        JFrame frame = new JFrame("bug6726866");
        frame.setUndecorated(true);
        setWindowNonOpaque(frame);

        JDesktopPane desktop = new JDesktopPane();
        desktop.setBackground(Color.GREEN);
        JInternalFrame iFrame = new JInternalFrame("Test", true, true, true, true);
        iFrame.add(new JLabel("internal Frame"));
        iFrame.setBounds(10, 10, 300, 200);
        iFrame.setVisible(true);
        desktop.add(iFrame);
        frame.add(desktop);

        frame.setSize(400, 400);
        return frame;
    }

    public static void setWindowNonOpaque(Window window) {
        Color bg = window.getBackground();
        if (bg == null) {
            bg = new Color(0, 0, 0, 0);
        }
        window.setBackground(
                new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 0));
    }
}
