/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8155197
 * @summary Tests whether the value of mostRecentFocusOwner for a window is correct, if
 *          another window is displayed during focus transition
 * @key headful
 * @library ../../regtesthelpers
 * @build Util
 * @run main FocusTransitionTest
 */

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import test.java.awt.regtesthelpers.Util;

public class FocusTransitionTest {
    private static Frame frame;
    private static TextField textField1;
    private static Button button;

    public static void main(String[] args) throws InterruptedException {
        try {
            Robot robot = Util.createRobot();

            createAndShowGUI();
            Util.waitForIdle(robot);

            for (int i = 0; i < 100; i++) {
                Util.clickOnComp(button, robot);

                synchronized (frame) {
                    frame.wait();
                }
                Util.waitForIdle(robot);

                Component focusOwner = frame.getMostRecentFocusOwner();
                if (focusOwner != textField1) {
                    throw new RuntimeException("Test FAILED: incorrect focus owner!");
                }
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }

    private static void createAndShowGUI() {
        frame = new Frame("Test Frame");

        textField1 = new TextField(5);

        button = new Button("Go");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.requestFocusInWindow();
                startUpdate();
            }
        });

        frame.setLayout(new FlowLayout());
        frame.setSize(400, 200);
        frame.add(textField1);
        frame.add(new TextField(5));
        frame.add(new TextField(5));
        frame.add(button);
        frame.setVisible(true);
    }

    private static void startUpdate() {
        UpdateThread updateThread = new UpdateThread(frame, 10);
        updateThread.start();
    }
}

class UpdateThread extends Thread {
    private final Frame frame;
    private final int delay;

    UpdateThread(Frame frame, int delay) {
        this.frame = frame;
        this.delay = delay;
    }

    @Override
    public void run() {
        Dialog dialog = new Dialog(frame);
        dialog.setSize(300, 100);
        dialog.setVisible(true);

        try {
            sleep(delay);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        dialog.setVisible(false);
        dialog.dispose();

        synchronized (frame) {
            frame.notify();
        }
    }
}

