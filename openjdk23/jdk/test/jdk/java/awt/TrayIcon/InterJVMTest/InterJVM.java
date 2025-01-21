/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @summary Check if TrayIcon added by a JVM is not visible
 *          in another JVM
 * @author Dmitriy Ermashov (dmitriy.ermashov@oracle.com)
 * @library ../../regtesthelpers/process/
 * @build ProcessResults ProcessCommunicator
 * @run main InterJVM
 */

import test.java.awt.regtesthelpers.process.ProcessCommunicator;
import test.java.awt.regtesthelpers.process.ProcessResults;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InterJVM {

    static String NEW_JVM = "-doTest";

    public static void main(String[] args) throws Exception {
        if (! SystemTray.isSupported()) {
            System.out.println("SystemTray not supported on the platform under test. " +
                    "Marking the test passed");
        } else {
            if (args == null || args.length == 0) {
                new InterJVM().addTrayIcon();
            } else {
                if (args.length == 1 && NEW_JVM.equals(args[0]))
                    new InterJVM().doTest();
            }
        }
    }

    void doTest() throws Exception {
        SystemTray tray = SystemTray.getSystemTray();
        try {
            TrayIcon[] icons = tray.getTrayIcons();
            System.out.println(icons.length);
            if (icons == null || icons.length != 0)
                throw new RuntimeException("FAIL: getTrayIcons() returned incorrect " +
                        "value when two icons are added by a " +
                        "separate JVM: " + icons.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void addTrayIcon() throws Exception {

        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon icon = new TrayIcon(new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB));
        tray.add(icon);

        new Robot().delay(2000);

        ProcessResults processResults =
                ProcessCommunicator.executeChildProcess(this.getClass(), new String[]{NEW_JVM});

        if (processResults.getExitValue() != 0)
            throw new RuntimeException("\n"+processResults.getStdErr());
    }
}
