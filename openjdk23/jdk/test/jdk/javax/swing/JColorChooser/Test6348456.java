/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.colorchooser.DefaultColorSelectionModel;

/*
 * @test
 * @bug 6348456
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Tests model changing
 * @run main/manual Test6348456
 */

public final class Test6348456 {

    private static final DefaultColorSelectionModel WHITE =
            new DefaultColorSelectionModel(Color.WHITE);
    private static final DefaultColorSelectionModel BLACK =
            new DefaultColorSelectionModel(Color.BLACK);

    private static JColorChooser chooser;

    public static void main(String[] args) throws Exception {
        String instructions = "When test starts, you'll see that the preview is white.\n" +
                "When you swap models, you'll see that the preview color is changed.\n" +
                "Click pass if so, otherwise fail.";

        PassFailJFrame.builder()
                .title("Test6348456")
                .instructions(instructions)
                .rows(5)
                .columns(40)
                .testTimeOut(10)
                .testUI(Test6348456::test)
                .build()
                .awaitAndCheck();
    }

    public static JFrame test() {
        JFrame frame = new JFrame("JColor Swap Models Test");
        JButton button = new JButton("Swap models");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                chooser.setSelectionModel(chooser.getSelectionModel() == BLACK ? WHITE : BLACK);

            }
        });

        chooser = new JColorChooser(Color.RED);
        chooser.setSelectionModel(WHITE);

        frame.add(BorderLayout.NORTH, button);
        frame.add(BorderLayout.CENTER, chooser);
        frame.pack();

        return frame;
    }
}
