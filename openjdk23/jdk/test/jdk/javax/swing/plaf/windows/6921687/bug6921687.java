/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 6921687 8079428 8155030
 * @summary Mnemonic disappears after repeated attempts to open menu items using
 *          mnemonics
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @requires (os.family == "windows")
 * @modules java.desktop/sun.swing
 * @run main bug6921687
 */

import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import jdk.test.lib.Platform;

import sun.swing.MnemonicHandler;

public class bug6921687 {

    private static Class lafClass;
    private static JFrame frame;

    public static void main(String[] args) throws Exception {
        if (!Platform.isWindows()) {
            System.out.println("Only Windows platform test. Test is skipped.");
            System.out.println("ok");
            return;
        }
        final String lafClassName = UIManager.getSystemLookAndFeelClassName();
        lafClass  = Class.forName(lafClassName);
        UIManager.setLookAndFeel(lafClassName);
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame();
                frame.setUndecorated(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setup(frame);
            });

            final Robot robot = new Robot();
            robot.waitForIdle();
            robot.setAutoDelay(20);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F);
            robot.keyRelease(KeyEvent.VK_F);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.waitForIdle();
            checkMnemonics();

            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.waitForIdle();
            checkMnemonics();
            System.out.println("ok");
        } finally {
            if (frame != null) { frame.dispose(); }
        }

    }

    private static void checkMnemonics() throws Exception {
        if (MnemonicHandler.isMnemonicHidden()) {
            throw new RuntimeException("Mnemonics are hidden");
        }
    }

    private static void setup(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // First Menu, F - Mnemonic
        JMenu firstMenu = new JMenu("First Menu");
        firstMenu.setMnemonic(KeyEvent.VK_F);
        firstMenu.add(new JMenuItem("One", KeyEvent.VK_O));
        firstMenu.add(new JMenuItem("Two", KeyEvent.VK_T));
        menuBar.add(firstMenu);

        // Second Menu, S - Mnemonic
        JMenu secondMenu = new JMenu("Second Menu");
        secondMenu.setMnemonic(KeyEvent.VK_S);
        secondMenu.add(new JMenuItem("A Menu Item", KeyEvent.VK_A));
        menuBar.add(secondMenu);

        frame.setSize(350, 250);
        frame.setVisible(true);
    }
}
