/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

/*
 * @test
 * @bug 4113040
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Checks that IMStatusBar does not affect Frame layout
 * @run main/manual/othervm -Duser.language=ja -Duser.country=JP IMStatusBar
 */

public class IMStatusBar {

    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                If the window appears the right size, but then resizes so that the
                status field overlaps the bottom label, press Fail; otherwise press Pass.
                """;

        PassFailJFrame.builder()
                .title("IMStatusBar Instruction")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(IMStatusBar::createUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createUI() {
        Frame f = new Frame();
        Panel centerPanel = new Panel();
        f.setSize(200, 200);
        f.setLayout(new BorderLayout());
        f.add(new Label("Top"), BorderLayout.NORTH);
        f.add(centerPanel, BorderLayout.CENTER);
        f.add(new Label("Bottom"), BorderLayout.SOUTH);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(new TextField("Middle"), BorderLayout.CENTER);
        centerPanel.validate();
        return f;
    }
}
