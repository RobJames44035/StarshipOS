/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8334366
 * @key headful printer
 * @summary Verifies original pageobject is returned unmodified
 *          on cancelling pagedialog
 * @requires (os.family == "windows")
 * @run main PageDialogCancelTest
 */

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

public class PageDialogCancelTest {

    public static void main(String[] args) throws Exception {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat oldFormat = new PageFormat();
        Robot robot = new Robot();
        Thread t1 = new Thread(() -> {
            robot.delay(2000);
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            robot.waitForIdle();
        });
        t1.start();
        PageFormat newFormat = pj.pageDialog(oldFormat);
        if (!newFormat.equals(oldFormat)) {
            throw new RuntimeException("Original PageFormat not returned on cancelling PageDialog");
        }
    }
}

