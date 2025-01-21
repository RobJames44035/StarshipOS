/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * @test
 * @bug 4150851
 * @summary Tests enter and exit events when a lightweight component is on a border
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual MouseEnterExitTest2
 */

public class MouseEnterExitTest2 {

    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                1. Verify that white component turns black whenever mouse enters the frame,
                   except when it enters the red rectangle.
                2. When the mouse enters the red part of the frame the component should stay white.
                """;
        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(EntryExitTest.initialize())
                .build()
                .awaitAndCheck();
    }
}

class EntryExitTest extends Component {
    boolean inWin;

    public Dimension getPreferredSize() {
        return new Dimension(200, 150);
    }

    public void paint(Graphics g) {
        Color c1, c2;
        String s;
        if (inWin) {
            c1 = Color.black;
            c2 = Color.white;
            s = "IN";
        } else {
            c2 = Color.black;
            c1 = Color.white;
            s = "OUT";
        }
        g.setColor(c1);
        Rectangle r = getBounds();
        g.fillRect(0, 0, r.width, r.height);
        g.setColor(c2);
        g.drawString(s, r.width / 2, r.height / 2);
    }

    public static Frame initialize() {
        EntryExitTest test = new EntryExitTest();
        MouseListener frameEnterExitListener = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                test.inWin = true;
                test.repaint();
            }

            public void mouseExited(MouseEvent e) {
                test.inWin = false;
                test.repaint();
            }
        };

        Frame f = new Frame("Mouse Modifier Test");

        f.add(test);
        Component jc = new Component() {
            public Dimension getPreferredSize() {
                return new Dimension(100, 50);
            }

            public void paint(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.red);
                g.fillRect(0, 0, d.width, d.height);
            }
        };
        final Container cont = new Container() {
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        cont.setLayout(new GridLayout(2, 1));
        cont.add(jc);
        jc.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                //System.out.println("Component entered");
            }
            public void mouseExited(MouseEvent e) {
                //System.out.println("Component exited");
            }
        });

        f.add(cont, BorderLayout.NORTH);
        f.addMouseListener(frameEnterExitListener);
        f.pack();
        return f;
    }
}
