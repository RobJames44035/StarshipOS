/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8290399
 * @requires (os.family == "mac")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Tests if AquaL&F fire actionevent if combobox menu is displayed.
 * @run main/manual JComboBoxActionEvent
 */

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class JComboBoxActionEvent {
    private static final String instructionsText = " Click the arrow to display the menu.\n" +
         "While the menu is displayed, edit the text to create a new value.\n" +
         "Type return.\n" +
         "If a dialog appears with \"ActionCommand received\"\n" +
         "press Pass, else Fail";

    private static JFrame frame;

    public static void createAndShowGUI() throws Exception {
        SwingUtilities.invokeAndWait(() -> {

            JComboBox<String> comboBox = new JComboBox<>(new String[]
                    { "Apple", "Orange", "Pear" });
            comboBox.setEditable(true);
            comboBox.addActionListener(e -> {
               System.out.println("Action Listener called: " + e.getActionCommand());
               if (e.getActionCommand().contains("comboBoxEdited")) {
                   JOptionPane.showMessageDialog(null, "ActionCommand received");
               }
            });

            FlowLayout layout = new FlowLayout();
            JPanel panel = new JPanel(layout);
            panel.add(comboBox);
            frame = new JFrame("Test Editable Combo Box");
            frame.getContentPane().add(panel);
            frame.setVisible(true);
            frame.pack();
            frame.setLocationRelativeTo(null);

            PassFailJFrame.addTestWindow(frame);
            PassFailJFrame.positionTestWindow(frame,
                    PassFailJFrame.Position.HORIZONTAL);
        });
    }

    public static void main(String[] args) throws Exception {

        UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");

        PassFailJFrame pfjFrame = new PassFailJFrame("JScrollPane "
                + "Test Instructions", instructionsText, 5);

        createAndShowGUI();

        pfjFrame.awaitAndCheck();
    }
}
