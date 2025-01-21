/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8326458 8155030
 * @key headful
 * @requires (os.family == "windows")
 * @modules java.desktop/sun.swing
 * @summary Verifies if menu mnemonics toggle on F10 press in Windows LAF
 * @run main TestMenuMnemonic
 */

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import sun.swing.MnemonicHandler;

public class TestMenuMnemonic {

    private static JFrame frame;
    private static JMenuBar menuBar;
    private static JMenu fileMenu;

    private static final AtomicInteger mnemonicHideCount = new AtomicInteger(0);
    private static final AtomicInteger mnemonicShowCount = new AtomicInteger(0);

    private static final int EXPECTED = 5;

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        Robot robot = new Robot();
        robot.setAutoDelay(100);

        try {
            SwingUtilities.invokeAndWait(TestMenuMnemonic::createAndShowUI);
            robot.waitForIdle();
            robot.delay(1000);

            for (int i = 0; i < EXPECTED * 2; i++) {
                robot.keyPress(KeyEvent.VK_F10);
                robot.waitForIdle();
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_F10);
                robot.waitForIdle();
                robot.delay(50);
                SwingUtilities.invokeAndWait(TestMenuMnemonic::verifyMnemonicsState);
            }

            if (mnemonicShowCount.get() != EXPECTED
                && mnemonicHideCount.get() != EXPECTED) {
                throw new RuntimeException("Mismatch in Mnemonic show/hide on F10 press");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    private static void verifyMnemonicsState() {
        MenuSelectionManager msm =
                MenuSelectionManager.defaultManager();
        MenuElement[] selectedPath = msm.getSelectedPath();
        if (MnemonicHandler.isMnemonicHidden()) {
            mnemonicHideCount.getAndIncrement();
            // Check if selection is cleared when mnemonics are hidden
            if (selectedPath.length != 0) {
                throw new RuntimeException("Menubar is active after" +
                        " mnemonics are hidden");
            }
        } else {
            mnemonicShowCount.getAndIncrement();
            if (selectedPath.length != 2
                && (selectedPath[0] != menuBar || selectedPath[1] != fileMenu)) {
                throw new RuntimeException("No Menu and Menubar is active when" +
                        " mnemonics are shown");
            }
        }
    }

    private static void createAndShowUI() {
        frame = new JFrame("Test Menu Mnemonic Show/Hide");
        menuBar  = new JMenuBar();
        fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        editMenu.setMnemonic(KeyEvent.VK_E);
        JMenuItem item1 = new JMenuItem("Item 1");
        JMenuItem item2 = new JMenuItem("Item 2");
        fileMenu.add(item1);
        fileMenu.add(item2);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);
        frame.setSize(250, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
