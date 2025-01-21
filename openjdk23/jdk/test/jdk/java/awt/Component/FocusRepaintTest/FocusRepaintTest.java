/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4079435
 * @summary Calling repaint() in focus handlers messes up the window.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FocusRepaintTest
 */

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;

public class FocusRepaintTest extends Frame implements FocusListener {
    static final String INSTRUCTIONS = """
            Hit the tab key repeatedly in the Test window.
            If any of the buttons disappear press Fail, otherwise press Pass.
            """;

    public FocusRepaintTest() {
        setTitle("Test");
        setLayout(new FlowLayout());
        setSize(200, 100);
        Button b1 = new Button("Close");
        Button b2 = new Button("Button");
        add(b1);
        add(b2);
        b1.setSize(50, 30);
        b2.setSize(50, 30);
        b1.addFocusListener(this);
        b2.addFocusListener(this);
    }

    public void focusGained(FocusEvent e) {
        Button b = (Button) e.getSource();
        PassFailJFrame.log("Focus gained for " + b.getLabel());
        b.repaint();
    }

    public void focusLost(FocusEvent e) {
        Button b = (Button) e.getSource();
        PassFailJFrame.log("Focus lost for " + b.getLabel());
        b.repaint();
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PassFailJFrame.builder()
                .title("Focus Repaint")
                .testUI(FocusRepaintTest::new)
                .instructions(INSTRUCTIONS)
                .columns(40)
                .logArea()
                .build()
                .awaitAndCheck();
    }
}
