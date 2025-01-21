/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
 * @bug 8064934
 * @key headful
 * @requires (os.family == "windows")
 * @summary Incorrect Exception message from java.awt.Desktop.open()
 * @author Dmitry Markov
 * @library /test/lib
 * @modules java.desktop/sun.awt
 * @build jdk.test.lib.Platform
 * @run main bug8064934
 */

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class bug8064934 {
    private static final String NO_ASSOCIATION_ERROR_MESSAGE = "Error message: No application is associated with" +
            " the specified file for this operation.";

    public static void main(String[] args) {

        // Test whether Desktop is supported of not
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        // Test whether open action is supported or not
        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            System.out.println("Desktop.Action.OPEN is not supported");
            return;
        }

        File file = null;
        try {
            file = File.createTempFile("test", ".foo");
            if (!file.exists()) {
                throw new RuntimeException("Can not create temp file");
            }
            desktop.open(file);
        } catch (IOException ioe) {
            String errorMessage = ioe.getMessage().trim();
            if (errorMessage != null && !errorMessage.endsWith(NO_ASSOCIATION_ERROR_MESSAGE)) {
                throw new RuntimeException("Test FAILED! Wrong Error message: \n" +
                        "Actual " + errorMessage.substring(errorMessage.indexOf("Error message:")) + "\n" +
                        "Expected " + NO_ASSOCIATION_ERROR_MESSAGE);
            }
        } finally {
            if (file != null) {
                file.delete();
            }
        }

        System.out.println("Test PASSED!");
    }
}
