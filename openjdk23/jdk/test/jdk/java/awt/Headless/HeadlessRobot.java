/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Robot;

/**
 * @test
 * @bug 8238741
 * @summary Check that Robot constructors throw AWTException in a headless
 *          environment
 * @run main/othervm -Djava.awt.headless=true HeadlessRobot
 */
public final class HeadlessRobot {

    public static void main(String[] args) {
        try {
            new Robot();
            throw new RuntimeException("Expected AWTException did not occur");
        } catch (AWTException ignored) {
            // expected AWTException
        }

        try {
            new Robot(new GraphicsDevice() {
                @Override
                public int getType() {
                    return TYPE_RASTER_SCREEN;
                }

                @Override
                public String getIDstring() {
                    return "Stub device";
                }

                @Override
                public GraphicsConfiguration[] getConfigurations() {
                    return null;
                }

                @Override
                public GraphicsConfiguration getDefaultConfiguration() {
                    return null;
                }
            });
            throw new RuntimeException("Expected AWTException did not occur");
        } catch (AWTException ignored) {
            // expected AWTException
        }
    }
}
