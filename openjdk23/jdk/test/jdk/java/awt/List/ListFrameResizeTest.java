/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4085379
 * @summary List component not properly "resized" with GridBagLayout
 * @requires (os.family == "windows")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ListFrameResizeTest
 */

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;

public class ListFrameResizeTest {

    private static final String INSTRUCTIONS = """
        This test is for windows only.

        1. A Frame will appear with a List
           (the List occupies the whole Frame)
        2. Minimize the Frame, the Frame is now in the Task Bar (ie.,iconified)
        3. Right click (right mouse button) the icon in the task bar
           and click on the 'maximize' menuitem to maximize the Frame
        4. If you notice the List has not been resized
           (ie.,if it partly occupies the Frame), then press FAIL else press PASS".""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("ListFrameResizeTest Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(ListFrameResizeTest::createTestUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createTestUI() {
        wintest client = new wintest("ListFrameResizeTest Frame");
        client.resize(500, 300);
        client.setBackground(Color.blue);
        return client;
    }

}

class wintest extends Frame {
    private List msg;

    public wintest(String title) {
        super(title);
        msg = new List();
        for (int i = 0; i < 100; i++) {
            msg.add("" + i);
        }

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        setLayout(gridbag);

        constraints.fill = GridBagConstraints.BOTH;

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(msg, constraints);
        add(msg);
    }
}
