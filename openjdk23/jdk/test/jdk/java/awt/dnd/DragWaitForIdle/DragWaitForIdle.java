/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.*;
import java.awt.event.InputEvent;

import test.java.awt.regtesthelpers.Util;

/**
 * @test
 * @key headful
 * @bug 7185258
 * @summary Robot.waitForIdle() should not hang forever if dnd is in progress
 * @library ../../regtesthelpers
 * @build Util
 * @run main/othervm DragWaitForIdle
 */
public final class DragWaitForIdle {

    public static void main(final String[] args) throws Exception {
        Frame frame = new Frame();
        Robot robot = new Robot();
        robot.setAutoWaitForIdle(true); // key point of the test

        DragGestureListener dragGestureListener = dge -> {
            dge.startDrag(null, new StringSelection("OK"), new DragSourceAdapter(){});
        };

        new DragSource().createDefaultDragGestureRecognizer(frame,
                DnDConstants.ACTION_MOVE, dragGestureListener);

        new DropTarget(frame, new DropTargetAdapter() {
            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                dtde.dropComplete(true);
            }
        });

        try {
            frame.setUndecorated(true);
            frame.setBounds(100, 100, 200, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            robot.waitForIdle();
            frame.toFront();

            Point startPoint = frame.getLocationOnScreen();
            Point endPoint = new Point(startPoint);
            startPoint.translate(50, 50);
            endPoint.translate(150, 150);

            Util.drag(robot, startPoint, endPoint, InputEvent.BUTTON2_MASK);

            robot.delay(500);
        } finally {
            frame.dispose();
        }
    }
}
