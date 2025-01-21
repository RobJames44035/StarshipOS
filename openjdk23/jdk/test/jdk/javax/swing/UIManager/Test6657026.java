/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6657026
 * @summary Tests shared UIManager in different application contexts
 * @author Sergey Malenkov
 * @modules java.desktop/sun.awt
 */

import sun.awt.SunToolkit;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Test6657026 implements Runnable {

    public static void main(String[] args) throws Exception {
        if (UIManager.getInstalledLookAndFeels().length == 0) {
            throw new Error("unexpected amount of look&feels");
        }
        UIManager.setInstalledLookAndFeels(new LookAndFeelInfo[0]);
        if (UIManager.getInstalledLookAndFeels().length != 0) {
            throw new Error("unexpected amount of look&feels");
        }

        ThreadGroup group = new ThreadGroup("$$$");
        Thread thread = new Thread(group, new Test6657026());
        thread.start();
        thread.join();
    }

    public void run() {
        SunToolkit.createNewAppContext();
        if (UIManager.getInstalledLookAndFeels().length == 0) {
            throw new Error("shared look&feels");
        }
    }
}
