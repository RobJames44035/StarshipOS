/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 4265389
 * @summary  Verifies JSplitPane support ComponentOrientation
 * @run main TestSplitPaneOrientationTest
 */

import java.awt.ComponentOrientation;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestSplitPaneOrientationTest {

    private static void setLookAndFeel(UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported LAF: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            System.out.println("Testing LAF : " + laf.getClassName());

            SwingUtilities.invokeAndWait(() -> {
                setLookAndFeel(laf);
                JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                                new JButton("Left"), new JButton("Right"));
                jsp.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                if (jsp.getRightComponent() instanceof JButton button) {
                    System.out.println(button.getText());
                    if (!button.getText().equals("Left")) {
                        throw new RuntimeException("JSplitPane did not support ComponentOrientation");
                    }
                }
            });
        }
    }

}

