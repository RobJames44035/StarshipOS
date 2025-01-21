/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

/*
 * @test
 * @bug 4222508
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Tests the color chooser disabling
 * @run main/manual Test4222508
 */
public final class Test4222508 {

    public static void main(String[] args) throws Exception {
        String instructions = "Click on colors in the JColorChooser.\n" +
                "Then uncheck the checkbox and click on colors again.\n" +
                "If the JColorChooser is disabled when the checkbox is unchecked, " +
                "then pass the test.";

        PassFailJFrame.builder()
                .title("Test4222508")
                .instructions(instructions)
                .rows(5)
                .columns(40)
                .testTimeOut(10)
                .testUI(Test4222508::test)
                .build()
                .awaitAndCheck();
    }

    public static JFrame test() {
        JFrame frame = new JFrame("JColorChooser with enable/disable checkbox");
        frame.setLayout(new BorderLayout());
        JColorChooser chooser = new JColorChooser();
        JCheckBox checkbox = new JCheckBox("Enable the color chooser below", true);
        checkbox.addItemListener(e -> chooser.setEnabled(checkbox.isSelected()));

        frame.add(chooser, BorderLayout.SOUTH);
        frame.add(checkbox, BorderLayout.NORTH);
        frame.pack();

        return frame;
    }

}
