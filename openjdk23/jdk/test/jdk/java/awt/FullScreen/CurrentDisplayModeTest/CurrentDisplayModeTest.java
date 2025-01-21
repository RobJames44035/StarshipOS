/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8022810
 * @summary Device.getDisplayMode() doesn't report refresh rate on Linux in case
 *          of dual screen
 * @run main CurrentDisplayModeTest
 */

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CurrentDisplayModeTest {
    public static void main(String[] args) {
        GraphicsDevice[] screenDevices = GraphicsEnvironment.
                               getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice screenDevice : screenDevices) {
            DisplayMode currentMode = screenDevice.getDisplayMode();
            System.out.println("current mode " + currentMode);
            Set<DisplayMode> set = new HashSet<>(
                                 Arrays.asList(screenDevice.getDisplayModes()));
            if (!set.contains(currentMode)) {
                throw new RuntimeException("Mode " + currentMode +
                        " is not found in the modes list " + set);
            }
        }
    }
}
