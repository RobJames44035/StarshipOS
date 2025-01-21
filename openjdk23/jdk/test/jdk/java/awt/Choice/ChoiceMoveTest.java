/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
  @test
  @bug 4323906
  @summary Test that Choice location is not updated erroneously
  @key headful
*/

/**
 * summary: The test adds a Choice to a Container with BorderLayout, and
 *          adds the Container to a Frame with null Layout.  When
 *          the Container is moved in the x direction, the Choice should
 *          not move in the y direction.
 */

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;

public class ChoiceMoveTest {

    static volatile Frame frame;
    static volatile Container c;
    static volatile Choice ch;

    public static void main(String[] args) throws Exception {
        try {
            EventQueue.invokeAndWait(() -> createUI());
            runTest();
        } finally {
            if (frame != null) {
                EventQueue.invokeAndWait(() -> frame.dispose());
            }
        }
    }

    static void createUI() {
        frame = new Frame("Choice Move Test");
        frame.setSize(500, 500);
        frame.setLayout(null);

        c = new Container();
        c.setBackground(Color.green);
        frame.add(c);
        c.setSize(200, 200);
        c.setLocation(100, 100);
        c.setLayout(new BorderLayout());

        ch = new Choice();
        ch.setSize(100, 27);
        c.add(ch, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.validate();
    }

    static void runTest () throws Exception {
        Thread.sleep(1000);
        // If this test ever gives us problems, try putting getLocation() in a
        // ComponentListener.
        int xbefore = ch.getLocation().x;
        int ybefore = ch.getLocation().y;
        System.out.println("Choice location before: " + xbefore + ", " + ybefore);

        c.setLocation(200, 100);
        Thread.sleep(1000);

        java.awt.Toolkit.getDefaultToolkit().sync();
        Thread.sleep(1000);
        int xafter = ch.getLocation().x;
        int yafter = ch.getLocation().y;
        System.out.println("Choice location after: " + xafter + ", " + yafter);

        if (ybefore != yafter) {
            System.out.println("Test FAILED");
            throw new RuntimeException("Test failed - Choice should not move in the y direction.");
        }
        else {
            System.out.println("Test passed.");
        }
    }
}
