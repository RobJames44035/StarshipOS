/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * @test
 * @key headful
 * @bug 8234522
 * @requires (os.family == "mac")
 * @summary no crash should occur when the "Go To Folder" feature is used on
 *          macOS in the native FileDialog
 */
public final class MacOSGoToFolderCrash {

    public static void main(final String[] args) throws Exception {
        EventQueue.invokeLater(() -> {
            FileDialog fd = new FileDialog((Frame) null);
            fd.setVisible(true);
        });
        Robot robot = new Robot();
        robot.setAutoDelay(400);
        robot.waitForIdle();
        // "CMD+Shift+G" - Open "Go To Folder" window
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_G);
        robot.keyRelease(KeyEvent.VK_G);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_META);
        // Select something
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        // Close File dialog
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }
}
