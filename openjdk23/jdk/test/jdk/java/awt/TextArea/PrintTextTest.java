/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.PrintJob;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 4075786
 * @key printer
 * @summary Test that container prints multiline text properly
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PrintTextTest
*/

public class PrintTextTest extends Frame implements ActionListener {
    Panel p;

    static final String INSTRUCTIONS = """
                Press "Print" button and check that multiline test
                printed correctly. If so press Pass, otherwise Fail.
                """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("PrintTextTest")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(PrintTextTest::new)
                .build()
                .awaitAndCheck();
    }

    public PrintTextTest() {
        p = new Panel();
        p.setLayout(new BorderLayout());
        TextArea text_area = new TextArea("multi\nline\ntext\nfield\nis \nhere\n!!!!\n");

        TextField text_field = new TextField("single line textfield");
        Button button = new Button("button");
        Label label = new Label("single line label");
        p.add("South", text_area);
        p.add("North", new TextArea("one single line of textarea"));
        p.add("Center", text_field);
        p.add("West", button);

        add("North", p);

        Button b = new Button("Print");
        b.addActionListener(this);
        add("South", b);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        PrintJob pjob = getToolkit().getPrintJob(this, "Print", null);
        if (pjob != null) {
            Graphics pg = pjob.getGraphics();

            if (pg != null) {
                p.printAll(pg);
                pg.dispose();  //flush page
            }
            pjob.end();
        }
    }
}
