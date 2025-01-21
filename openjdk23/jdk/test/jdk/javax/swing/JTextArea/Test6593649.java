/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 6593649
 * @summary Word wrap does not work in JTextArea: long lines are not wrapped
 * @author Lillian Angel
 * @run main Test6593649
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test6593649 {
    private static JFrame frame;

    private static JTextArea textArea;

    private static final Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean failed = !textArea.getParent().getSize().equals(textArea.getSize());

            frame.dispose();

            if (failed) {
                throw new RuntimeException("The test failed");
            }
        }
    });

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                frame = new JFrame();

                frame.setSize(200, 100);

                textArea = new JTextArea("This is a long line that should wrap, but doesn't...");

                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JPanel innerPanel = new JPanel();

                innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.LINE_AXIS));
                innerPanel.add(textArea);

                frame.getContentPane().add(innerPanel, BorderLayout.SOUTH);

                frame.setVisible(true);

                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}
