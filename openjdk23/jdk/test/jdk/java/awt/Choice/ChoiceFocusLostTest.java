/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
  @test
  @bug 4338368
  @summary Tests that choice doesn't throw spurious mouse events when losing focus
  @key headful
*/

import java.awt.Button;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Robot;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChoiceFocusLostTest {

    public static void main(String[] args) throws Exception {
        try {
            EventQueue.invokeAndWait(() -> createUI());
            Robot robot = new Robot();
            robot.waitForIdle();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(50);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.waitForIdle();
            robot.delay(1000);
            if (!client.isPassed()) {
                throw new RuntimeException("Test failed: choice fires spurious events");
            } else {
                System.out.println("Test passed.");
            }
        } finally {
            if (frame != null) {
                EventQueue.invokeAndWait(() -> frame.dispose());
            }
        }
    }

    static volatile Frame frame;
    static volatile ChoiceBug client;

    static void createUI() {
        frame = new Frame("ChoiceFocusLostTest");
        client = new ChoiceBug();
        frame.add(client);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class ChoiceBug extends Panel {

    volatile boolean passed = true;

    public ChoiceBug() {
        Choice choice = new Choice();
        choice.add("item-1");
        choice.add("item-2");
        Button button = new Button("Button");
        add(choice);
        add(button);
        choice.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent me) {
                passed = false;
            }
            public void mouseClicked(MouseEvent me) {
                passed = false;
            }
        });
        choice.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent fe) {
                System.out.println("Focus Gained");
                System.out.println(fe);
            }
            public void focusLost(FocusEvent fe) {
                System.out.println("Got expected FocusLost event.");
                System.out.println(fe);
            }
        });
        setSize(400, 400);
        choice.requestFocus();
    }

    public boolean isPassed() {
        return passed;
    }
}
