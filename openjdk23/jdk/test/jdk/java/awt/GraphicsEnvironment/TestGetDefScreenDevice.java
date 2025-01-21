/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6896335
 * @summary Test GraphicsEnvironment.getDefaultScreenDevice() in headless mode
 * @run main/othervm -Djava.awt.headless=true TestGetDefScreenDevice
 */

import java.awt.*;
public class TestGetDefScreenDevice {

    public static void main(String[] args) throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.getDefaultScreenDevice();
            throw new Exception("Failed. HeadlessException not thrown");
        } catch (HeadlessException he) {
            // OK, test passed.
        }
    }
}
