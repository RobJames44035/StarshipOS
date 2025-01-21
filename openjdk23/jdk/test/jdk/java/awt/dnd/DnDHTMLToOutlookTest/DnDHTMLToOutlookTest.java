/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;


/*
 * @test
 * @bug 6392086
 * @summary Tests dnd to another screen
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual DnDHTMLToOutlookTest
 */

public class DnDHTMLToOutlookTest {

    private static final String INSTRUCTIONS = """
            The window contains a yellow button. Click on the button
            to copy HTML from DnDSource.html file into the clipboard or drag
            HTML context. Paste into or drop over the HTML capable editor in
            external application such as Outlook, Word.

            When the mouse enters the editor, cursor should change to indicate
            that copy operation is about to happen and then release the mouse
            button. HTML text without tags should appear inside the document.

            You should be able to repeat this operation multiple times.
            If the above is true Press PASS else FAIL.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                      .title("Test Instructions")
                      .instructions(INSTRUCTIONS)
                      .columns(40)
                      .testUI(DnDHTMLToOutlookTest::createAndShowUI)
                      .build()
                      .awaitAndCheck();
    }

    private static Frame createAndShowUI() {
        Frame frame = new Frame("DnDHTMLToOutlookTest");
        Panel mainPanel;
        Component dragSource;

        mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.setBackground(Color.YELLOW);
        dragSource = new DnDSource("Drag ME (HTML)!");

        mainPanel.add(dragSource, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setSize(200, 200);
        return frame;
    }
}
