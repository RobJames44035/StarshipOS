/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @summary JVM crash if the frame is disposed in DropTargetListener
 * @bug 8252470
 * @key headful
 * @author Petr Pchelko
 * @library ../../regtesthelpers
 * @build Util
 * @compile DisposeFrameOnDragTest.java
 * @run main/othervm DisposeFrameOnDragTest
 */
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.TooManyListenersException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import test.java.awt.regtesthelpers.Util;

public class DisposeFrameOnDragTest {

    private static JTextArea textArea;
    private static JFrame background;

    public static void main(String[] args) throws Throwable {

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                constructTestUI();
            }
        });

        Robot testRobot = null;
        try {
            testRobot = new Robot();
        } catch(AWTException ex) {
            throw new RuntimeException("Error while creating Robot");
        }

        Util.waitForIdle(testRobot);

        Point loc = textArea.getLocationOnScreen();
        Util.drag(testRobot,
                new Point((int) loc.x + 3, (int) loc.y + 3),
                new Point((int) loc.x + 40, (int) loc.y + 40),
                InputEvent.BUTTON1_DOWN_MASK);

        Util.waitForIdle(testRobot);

        testRobot.delay(200);
        background.dispose();
    }

    private static void constructTestUI() {
        background = new JFrame("Background");
        background.setBounds(100, 100, 100, 100);
        background.setUndecorated(true);
        background.setVisible(true);

        final JFrame frame = new JFrame("Test frame");
        textArea = new JTextArea("Drag Me!");
        try {
            textArea.getDropTarget().addDropTargetListener(new DropTargetAdapter() {
                @Override
                public void drop(DropTargetDropEvent dtde) {
                    //IGNORE
                }

                @Override
                public void dragOver(DropTargetDragEvent dtde) {
                    frame.dispose();
                }
            });
        } catch (TooManyListenersException ex) {
            throw new RuntimeException(ex);
        }
        textArea.setSize(100, 100);
        textArea.setDragEnabled(true);
        textArea.select(0, textArea.getText().length());
        frame.add(textArea);
        frame.setBounds(100, 100, 100, 100);
        frame.setVisible(true);
    }
}
