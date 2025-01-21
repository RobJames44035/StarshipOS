/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.Window;
/**
 * @test
 * @key headful
 * @bug 7081594
 * @author Alexander Scherbatiy
 * @summary Windows owned by an always-on-top window DO NOT automatically become always-on-top
 * @run main AlwaysOnTopFieldTest
 */
public class AlwaysOnTopFieldTest {

    public static void main(String[] args) {
        Robot robot;
        try {
            robot = new Robot();
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unexpected failure");
        }

        Window window = new Frame("Window 1");
        window.setSize(200, 200);
        window.setAlwaysOnTop(true);
        window.setVisible(true);
        robot.waitForIdle();

        Dialog dialog = new Dialog(window, "Owned dialog 1");
        dialog.setSize(200, 200);
        dialog.setLocation(100, 100);
        dialog.setVisible(true);
        robot.waitForIdle();

        try {
            if (!window.isAlwaysOnTop()) {
                throw new RuntimeException("Window has wrong isAlwaysOnTop value");
            }
            if (!dialog.isAlwaysOnTop()) {
                throw new RuntimeException("Dialog has wrong isAlwaysOnTop value");
            }
        } finally {
            window.dispose();
            dialog.dispose();
        }

        window = new Frame("Window 2");
        window.setSize(200, 200);
        window.setVisible(true);
        robot.waitForIdle();


        dialog = new Dialog(window, "Owned dialog 2");
        dialog.setSize(200, 200);
        dialog.setLocation(100, 100);
        dialog.setVisible(true);
        robot.waitForIdle();

        window.setAlwaysOnTop(true);
        robot.waitForIdle();

        try {
            if (!window.isAlwaysOnTop()) {
                throw new RuntimeException("Window has wrong isAlwaysOnTop value");
            }
            if (!dialog.isAlwaysOnTop()) {
                throw new RuntimeException("Dialog has wrong isAlwaysOnTop value");
            }
        } finally {
            window.dispose();
            dialog.dispose();
        }
    }
}
