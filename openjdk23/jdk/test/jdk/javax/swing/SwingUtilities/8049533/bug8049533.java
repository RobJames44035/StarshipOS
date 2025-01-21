/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseWheelEvent;
import javax.swing.SwingUtilities;

/**
 * @test
 * @key headful
 * @bug 8049533
 * @summary SwingUtilities.convertMouseEvent misses
 *      MouseWheelEvent.preciseWheelRotation
 * @run main bug8049533
 */
public class bug8049533 {

    private static final double PRECISE_WHEEL_ROTATION = 3.14;

    public static void main(String[] args) {
        Frame frame = new Frame();
        Panel panel = new Panel();
        frame.add(panel);

        MouseWheelEvent event = new MouseWheelEvent(panel,
                0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0,
                2, // wheelRotation
                PRECISE_WHEEL_ROTATION); // preciseWheelRotation

        MouseWheelEvent convertedEvent = (MouseWheelEvent) SwingUtilities.
                convertMouseEvent(event.getComponent(), event, null);

        if (convertedEvent.getPreciseWheelRotation() != PRECISE_WHEEL_ROTATION) {
            throw new RuntimeException("PreciseWheelRotation field is not copied!");
        }
    }
}
