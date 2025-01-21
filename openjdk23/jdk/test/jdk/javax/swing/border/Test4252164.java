/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/*
 * @test
 * @bug 4252164 8041917
 * @summary Tests rounded LineBorder for components
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual Test4252164
 */

public class Test4252164 {
    private static int thickness;
    private static JLabel rounded;
    private static JLabel straight;

    public static void main(String[] args) throws Exception {
        String testInstructions = """
                Please, ensure that rounded border is filled completely.
                It should not contain white points inside.
                Use Mouse Wheel to change thickness of the border.
                                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(testInstructions)
                .rows(4)
                .columns(35)
                .splitUI(Test4252164::createUI)
                .build()
                .awaitAndCheck();
    }

    private static JPanel createUI() {
        rounded = new JLabel("ROUNDED"); // NON-NLS: the label for rounded border
        straight = new JLabel("STRAIGHT"); // NON-NLS: the label for straight border
        JPanel panel = new JPanel();
        panel.add(rounded);
        panel.add(straight);
        update(10);
        panel.addMouseWheelListener(e -> update(e.getWheelRotation()));
        return panel;
    }

    private static void update(int thicknessValue) {
        thickness += thicknessValue;
        rounded.setBorder(new LineBorder(Color.RED, thickness, true));
        straight.setBorder(new LineBorder(Color.RED, thickness, false));
    }
}
