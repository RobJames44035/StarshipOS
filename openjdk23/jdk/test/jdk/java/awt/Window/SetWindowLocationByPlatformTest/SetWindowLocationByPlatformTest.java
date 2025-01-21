/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8025130
 * @summary setLocationByPlatform has no effect
 * @author Dmitry Markov
 * @library ../../regtesthelpers
 * @build Util
 * @run main SetWindowLocationByPlatformTest
 */

import java.awt.*;

import test.java.awt.regtesthelpers.Util;

public class SetWindowLocationByPlatformTest {
    public static void main(String[] args) {
        Robot r = Util.createRobot();

        Frame frame1 = new Frame ("First Frame");
        frame1.setSize(500, 300);
        frame1.setLocationByPlatform(true);
        frame1.setVisible(true);
        Util.waitForIdle(r);

        Frame frame2 = new Frame ("Second Frame");
        frame2.setSize(500, 300);
        frame2.setLocationByPlatform(true);
        frame2.setVisible(true);
        Util.waitForIdle(r);
        r.delay(500);

        Point point1 = frame1.getLocationOnScreen();
        Point point2 = frame2.getLocationOnScreen();

        try {
            if (point1.equals(point2)) {
                throw new RuntimeException("Test FAILED: both frames have the same location " + point1);
            }
        } finally {
            frame1.dispose();
            frame2.dispose();
        }
    }
}

