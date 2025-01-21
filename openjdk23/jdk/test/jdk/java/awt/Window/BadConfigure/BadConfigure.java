/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6261336
 * @summary Tests that Choice inside ScrollPane opens at the right location
 *          after resize
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual BadConfigure
*/

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Frame;

public class BadConfigure
{
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
            Please resize the BadConfigure window using the left border.
            Now click on choice. Its popup will be opened.
            Please verify that the popup is opened right under the choice.
            """;

        PassFailJFrame.builder()
            .title("Test Instructions")
            .instructions(INSTRUCTIONS)
            .columns(35)
            .testUI(initialize())
            .build()
            .awaitAndCheck();
    }

    private static Frame initialize() {
        Frame f = new Frame("BadConfigure");
        f.setLayout(new BorderLayout());
        Choice ch = new Choice();
        f.add(ch, BorderLayout.NORTH);
        ch.add("One");
        ch.add("One");
        ch.add("One");
        ch.add("One");
        ch.add("One");
        ch.add("One");
        f.setSize(200, 200);
        f.validate();
        return f;
    }
}
