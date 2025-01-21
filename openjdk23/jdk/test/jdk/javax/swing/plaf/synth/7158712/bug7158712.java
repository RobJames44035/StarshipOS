/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 7158712
 * @summary Synth Property "ComboBox.popupInsets" is ignored
 * @library ../../../regtesthelpers
 * @run main/othervm -Dsun.java2d.uiScale=1 bug7158712
 */

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;

public class bug7158712 {
    private static final String SYNTH_XML = "<synth>" +
            "    <style id=\"all\">" +
            "      <font name=\"Dialog\" size=\"12\"/>" +
            "    </style>" +
            "    <bind style=\"all\" type=\"REGION\" key=\".*\"/>" +
            "    <style id=\"arrowButton\">" +
            "      <property key=\"ArrowButton.size\" type=\"integer\" value=\"18\"/>" +
            "    </style>" +
            "    <bind style=\"arrowButton\" type=\"region\" key=\"ArrowButton\"/>" +
            "    <style id=\"comboBox\">" +
            "      <property key=\"ComboBox.popupInsets\" type=\"insets\" value=\"-5 -5 5 -5\"/>" +
            "    </style>" +
            "    <bind style=\"comboBox\" type=\"region\" key=\"ComboBox\"/>" +
            "</synth>";

    private static JComboBox<String> comboBox;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();

        robot.setAutoDelay(100);

        SynthLookAndFeel laf = new SynthLookAndFeel();

        laf.load(new ByteArrayInputStream(SYNTH_XML.getBytes("UTF8")), bug7158712.class);

        UIManager.setLookAndFeel(laf);

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                comboBox = new JComboBox<>(
                        new String[]{"Very Looooooooooooooooooooong Text Item 1", "Item 2"});

                JFrame frame = new JFrame();

                frame.add(comboBox, BorderLayout.NORTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(400, 300));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        robot.waitForIdle();
        robot.delay(1000);

        Point comboBoxLocation = Util.invokeOnEDT(new Callable<Point>() {
            @Override
            public Point call() throws Exception {
                return comboBox.getLocationOnScreen();
            }
        });

        robot.mouseMove(comboBoxLocation.x, comboBoxLocation.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                BasicComboPopup popup = (BasicComboPopup) comboBox.getAccessibleContext().getAccessibleChild(0);

                Point popupPoint = popup.getLocationOnScreen();
                Point comboBoxPoint = comboBox.getLocationOnScreen();

                if (comboBoxPoint.x - 5 != popupPoint.x ||
                        comboBoxPoint.y + comboBox.getHeight() - 5 != popupPoint.y) {
                    throw new RuntimeException("Invalid popup coordinates. Popup location: " + popupPoint +
                            ", comboBox location: " + comboBoxPoint);
                }

                System.out.println("Test bug7158712 passed");
            }
        });
    }
}
