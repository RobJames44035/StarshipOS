/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4117288
 * @summary JDKversion1.2beta3-J List's add() method is much slower.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ListAddPerfTest
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListAddPerfTest {

    static Button button;
    static List list;

    private static final String INSTRUCTIONS = """
         It is used to check the performance of List add operation.

         Instructions:
             Click on the Remove All button.
             The list should be cleared.
             The button is now named "Add Items".
             Click on the "Add Items" button.
             800 items should be added to the list.
             Notice not only how fast or slow this is, but also how
             'smooth' it goes as well i.e. without any flashing.

             Press pass if the list performance is acceptable.""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("ListAddPerfTest Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int)INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(ListAddPerfTest::createTestUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createTestUI() {
        Frame frame = new Frame("ListAddPerfTest");
        frame.setLayout(new BorderLayout());

        button = new Button("Remove All");
        button.addActionListener((ActionEvent e) -> {
            if (list.getItemCount() > 0) {
                list.removeAll();
                list.invalidate();
                button.setLabel("Add Items");
            } else {
                for (int i = 0; i < 800; i ++) {
                    list.add("My number is " + i);
                }
                button.setLabel("Remove All");
            }
        });

        list = new List(15);
        for (int i = 0; i < 800; i ++) {
            list.add("My number is " + i);
        }

        frame.add("North", button);
        frame.add("South", list);

        frame.pack();
        return frame;
    }
}
