/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4088173
 * @summary This interactive test verifies that the XOR mode is not affecting
 *          the clearRect() call. The correct output looks like:
 *
 *          \      /
 *           \    /
 *                     The backgound is blue.
 *                     The lines outside the central rectangle are green.
 *                     The central rectangle is also blue (the result of clearRect())
 *           /    \
 *          /      \
 *
 * @key headful
 * @run main XORClearRect
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Robot;

public class XORClearRect extends Panel {

    public static void main(String args[]) throws Exception {
        EventQueue.invokeAndWait(XORClearRect::createUI);
        try {
             Robot robot = new Robot();
             robot.waitForIdle();
             robot.delay(2000);
             Point p = frame.getLocationOnScreen();
             int pix = robot.getPixelColor(p.x + 100, p.y + 100).getRGB();
             if (pix != Color.blue.getRGB()) {
                 throw new RuntimeException("Not blue");
             }
        } finally {
            if (frame != null) {
                EventQueue.invokeAndWait(frame::dispose);
            }
        }
    }

    static volatile Frame frame;

    static void createUI() {
        frame = new Frame("XORClearRect");
        frame.setBackground(Color.blue);
        XORClearRect xor = new XORClearRect();
        frame.add(xor);
        frame.setSize(200,200);
        frame.setVisible(true);
    }

    public XORClearRect() {
       setBackground(Color.blue);
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.drawLine(0,0,200,200);
        g.drawLine(0,200,200,0);
        g.setXORMode(Color.blue);
        g.clearRect(50,50,100,100); //expecting the rectangle to be filled
                                    // with the background color (blue)
    }
}
