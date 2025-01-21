/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * @test
 * @bug 4814163 5005195
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Tests events fired by CheckboxMenuItem
 * @run main/manual CheckboxMenuItemEventsTest
*/

public class CheckboxMenuItemEventsTest extends Frame implements ActionListener {
    Button trigger;
    PopupMenu popup;
    TextArea ta;

    class Listener implements ItemListener, ActionListener {
        public void itemStateChanged(ItemEvent e) {
            ta.append("CORRECT: ItemEvent fired\n");
        }

        public void actionPerformed(ActionEvent e) {
            ta.append("ERROR: ActionEvent fired\n");
        }
    }

    Listener listener = new Listener();

    private static final String INSTRUCTIONS = """
            Press button to invoke popup menu
            When you press checkbox menu item
            Item state should toggle (on/off).
            ItemEvent should be displayed in log below.
            And ActionEvent should not be displayed
            Press PASS if ItemEvents are generated
            and ActionEvents are not, FAIL Otherwise.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("CheckboxMenuItemEventsTest")
                .instructions(INSTRUCTIONS)
                .columns(35)
                .testUI(CheckboxMenuItemEventsTest::new)
                .build()
                .awaitAndCheck();
    }

    public CheckboxMenuItemEventsTest() {
        CheckboxMenuItem i1 = new CheckboxMenuItem("CheckBoxMenuItem 1");
        CheckboxMenuItem i2 = new CheckboxMenuItem("CheckBoxMenuItem 2");
        Panel p1 = new Panel();
        Panel p2 = new Panel();

        setLayout(new BorderLayout());
        ta = new TextArea();
        p2.add(ta);

        trigger = new Button("menu");
        trigger.addActionListener(this);

        popup = new PopupMenu();

        i1.addItemListener(listener);
        i1.addActionListener(listener);
        popup.add(i1);
        i2.addItemListener(listener);
        i2.addActionListener(listener);
        popup.add(i2);

        trigger.add(popup);

        p1.add(trigger);

        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.SOUTH);

        pack();
        validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == (Object) trigger) {
            popup.show(trigger, trigger.getSize().width, 0);
        }
    }
}
