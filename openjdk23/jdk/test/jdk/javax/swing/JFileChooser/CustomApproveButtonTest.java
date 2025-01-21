/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * @test
 * @bug 8299522 8300084
 * @key headful
 * @summary Verifies JFileChooser's Approve button text is non-null
 *          when CUSTOM_DIALOG type is set.
 * @run main CustomApproveButtonTest
 */

public class CustomApproveButtonTest {
    private JFrame frame;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                UIManager.LookAndFeelInfo[] lookAndFeel = UIManager.getInstalledLookAndFeels();
                for (UIManager.LookAndFeelInfo look : lookAndFeel) {
                    new CustomApproveButtonTest(look.getClassName());
                }
            }
        });
        System.out.println("Test Pass!");
    }

    private CustomApproveButtonTest(String lookAndFeel) {
        System.out.println("Testing Look & Feel : " + lookAndFeel);

        setLookAndFeel(lookAndFeel);

        try {
            frame = new JFrame("CustomApproveButtonTest");

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
            frame.add(fileChooser);
            frame.pack();
            frame.setVisible(true);

            JButton customApproveButton = fileChooser.getUI().getDefaultButton(fileChooser);

            if (customApproveButton == null) {
                throw new RuntimeException("Cannot find Approve button in FileChooser!");
            }
            if (customApproveButton.getText() == null) {
                throw new RuntimeException("Approve button text is null in FileChooser!");
            }
            if (customApproveButton.getToolTipText() == null) {
                throw new RuntimeException("Approve button tooptip is null in FileChooser!");
            }
            if (customApproveButton.getMnemonic() != 0) {
                throw new RuntimeException("Approve button mnemonic is non-zero in FileChooser!");
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }

    private static void setLookAndFeel(String laf) {
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported L&F: " + laf);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
