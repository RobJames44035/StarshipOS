/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6852592
  @summary invalidate() must stop when it encounters a validate root
  @author anthony.petrov@sun.com
  @run main/othervm -Djava.awt.smartInvalidate=true InvalidateMustRespectValidateRoots
*/

import javax.swing.*;
import java.awt.event.*;

public class InvalidateMustRespectValidateRoots {
    private static volatile JRootPane rootPane;

    public static void main(String args[]) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                // The JRootPane is a validate root. We'll check if
                // invalidate() stops on the root pane, or goes further
                // up to the frame.
                JFrame frame = new JFrame();
                final JButton button = new JButton();

                frame.add(button);

                // To enable running the test manually: use the Ctrl-Shift-F1
                // to print the component hierarchy to the console
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        if (button.isValid()) {
                            button.invalidate();
                        } else {
                            button.revalidate();
                        }
                    }
                });

                rootPane = frame.getRootPane();

                // Now the component hierarchy looks like:
                // frame
                // --> rootPane
                //     --> layered pane
                //         --> content pane
                //             --> button

                // Make all components valid first via showing the frame
                // We have to make the frame visible. Otherwise revalidate() is
                // useless (see RepaintManager.addInvalidComponent()).
                frame.pack(); // To enable running this test manually
                frame.setVisible(true);

                if (!frame.isValid()) {
                    throw new RuntimeException(
                            "setVisible(true) failed to validate the frame");
                }

                // Now invalidate the button
                button.invalidate();

                // Check if the 'valid' status is what we expect it to be
                if (rootPane.isValid()) {
                    throw new RuntimeException(
                            "invalidate() failed to invalidate the root pane");
                }

                if (!frame.isValid()) {
                    throw new RuntimeException(
                            "invalidate() invalidated the frame");
                }

                // Now validate the hierarchy again
                button.revalidate();

                // Now let the validation happen on the EDT
            }
        });

        Thread.sleep(1000);

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                // Check if the root pane finally became valid
                if (!rootPane.isValid()) {
                    throw new RuntimeException(
                            "revalidate() failed to validate the hierarchy");
                }
            }
        });
    }
}
