/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6391547
 * @summary Test if the JTextField's cursor is changed when there is a modal dialog
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual BlockedWindowTest
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyDialog extends Dialog implements ActionListener {
    MyDialog(Frame owner) {
        super(owner, "Modal dialog", true);
        setBounds(owner.getX() + 150, owner.getY() + 150, 100, 100);
        setLayout(new BorderLayout());
        Button b = new Button("Close");
        add(b, "South");
        b.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        this.dispose();
    }
}

class MyFrame extends Frame implements ActionListener {
    Dialog d;

    public MyFrame() {
        super("ManualYesNoTest");
        Button b = new Button("Click here");
        TextField tf = new TextField("A text field");
        b.addActionListener(this);
        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b, "South");
        add(tf, "North");
        setSize(300, 300);
    }

    public void actionPerformed(ActionEvent ae) {
        d = new MyDialog(this);
    }
}

public class BlockedWindowTest {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                Verify that the hand cursor is displayed over the window and
                text cursor over TextField.
                Click the button in the window to display a modal dialog.
                Verify that default cursor is displayed over the window
                and over TextField now.
                Then close modal dialog and verify that hand cursor is
                displayed over window and text cursor over TextField.
                If so, press PASS, else press FAIL.
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .columns(35)
                .testUI(BlockedWindowTest::createUI)
                .build()
                .awaitAndCheck();
    }

    public static MyFrame createUI() {
        MyFrame f = new MyFrame();
        return f;
    }
}
