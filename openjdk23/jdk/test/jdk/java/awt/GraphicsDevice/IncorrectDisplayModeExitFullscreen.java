/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */


import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * @test
 * @key headful
 * @bug 8019587
 * @author Sergey Bylokhov
 * @library /lib/client/
 * @build ExtendedRobot
 * @run main IncorrectDisplayModeExitFullscreen
 */
public class IncorrectDisplayModeExitFullscreen {
    static ExtendedRobot robot;

    public static void main(final String[] args) {

        final GraphicsDevice[] devices =
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                                   .getScreenDevices();
        if (devices.length < 2 || devices[0].getDisplayModes().length < 2
                || !devices[0].isDisplayChangeSupported()
                || !devices[0].isFullScreenSupported()
                || !devices[1].isFullScreenSupported()) {
            System.err.println("Testcase is not applicable");
            return;
        }
        final DisplayMode defaultDM = devices[0].getDisplayMode();
        final DisplayMode[] dms = devices[0].getDisplayModes();
        DisplayMode nonDefaultDM = null;

        for (final DisplayMode dm : dms) {
            if (!dm.equals(defaultDM)) {
                nonDefaultDM = dm;
                break;
            }
        }
        if (nonDefaultDM == null) {
            System.err.println("Testcase is not applicable");
            return;
        }

        try {
            robot = new ExtendedRobot();
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unexpected failure");
        }

        final Frame frame = new Frame();
        frame.setBackground(Color.GREEN);
        frame.setUndecorated(true);
        try {
            devices[0].setFullScreenWindow(frame);
            sleep();
            devices[0].setDisplayMode(nonDefaultDM);
            sleep();
            devices[1].setFullScreenWindow(frame);
            sleep();
            if (!defaultDM.equals(devices[0].getDisplayMode())) {
                throw new RuntimeException("DisplayMode is not restored");
            }
        } finally {
            // cleaning up
            devices[0].setFullScreenWindow(null);
            devices[1].setFullScreenWindow(null);
            frame.dispose();
        }
    }
    private static void sleep() {
        robot.waitForIdle(1500);
    }
}
