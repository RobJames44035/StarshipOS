/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4750421
 * @summary 4143833 - regression in 1.4.x
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual bug4750421
 */

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

public class bug4750421 {

    private static final String INSTRUCTIONS = """
        A table will be shown.
        Select the third row of the table.
        Then press down arrow button of vertical scrollbar to scroll down.
            (in macos drag the vertical scrollbar down via mouse just enough
            to scroll by 1 unit as there is no arrow button in scrollbar)
        If the selection disappears press Fail else press Pass.""";

    private static JFrame createTestUI() {
        JFrame frame = new JFrame("bug4750421");
        JTable table = new JTable(30, 10);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table);
        frame.getContentPane().add(pane);
        pane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        frame.pack();
        return frame;
    }

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("JViewport Instructions")
                .instructions(INSTRUCTIONS)
                .rows(7)
                .columns(35)
                .testUI(bug4750421::createTestUI)
                .build()
                .awaitAndCheck();
    }
}
