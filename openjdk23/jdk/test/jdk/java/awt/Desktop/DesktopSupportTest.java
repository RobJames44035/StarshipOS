/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6255196
 * @key headful
 * @summary Verifies if class Desktop is supported on current platform.
 * @run main DesktopSupportTest
 */

import java.awt.Desktop;

public class DesktopSupportTest {
    public static void main(String[] args) {
        boolean supported = Desktop.isDesktopSupported();
        try {
            Desktop desktop = Desktop.getDesktop();
            if (!supported) {
                throw new RuntimeException("UnsupportedOperationException " +
                        "should be thrown, as this class is not supported " +
                        "on current platform.");
            }
        } catch (UnsupportedOperationException uoe) {
            if (supported) {
                throw new RuntimeException("UnsupportedOperationException " +
                        "should NOT be thrown, as this class is supported " +
                        "on current platform.");
            }
        } catch (Exception e) {
            if (!supported) {
                throw new RuntimeException("UnsupportedOperationException " +
                        "should be thrown, as this class is not supported " +
                        "on current platform. But " + e.getClass().getName() +
                        " has been thrown instead.");
            }
        }
    }
}
