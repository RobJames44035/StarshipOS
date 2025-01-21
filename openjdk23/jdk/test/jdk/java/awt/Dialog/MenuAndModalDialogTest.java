/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 4070085
 * @summary Java program locks up X server
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual MenuAndModalDialogTest
 */

public class MenuAndModalDialogTest {
    static Frame frame;
    static String instructions = """
            1. Bring up the File Menu and leave it up.
            2. In a few seconds, the modal dialog will appear.
            3. Verify that your system does not lock up when you push the "OK" button.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame pf = PassFailJFrame.builder()
                .title("MenuAndModalDialogTest")
                .instructions(instructions)
                .testTimeOut(5)
                .rows(10)
                .columns(35)
                .testUI(MenuAndModalDialogTest::createFrame)
                .build();

        // Allow time to pop up the menu
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException exception) {
        }

        createDialog();
        pf.awaitAndCheck();
    }

    public static Frame createFrame() {
        frame = new Frame("MenuAndModalDialogTest frame");

        MenuBar menuBar = new MenuBar();
        frame.setMenuBar(menuBar);

        Menu file = new Menu("File");
        menuBar.add(file);

        MenuItem menuItem = new MenuItem("A Menu Entry");
        file.add(menuItem);

        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    public static void createDialog() {
        Dialog dialog = new Dialog(frame);

        Button button = new Button("OK");
        dialog.add(button);
        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                }
        );

        dialog.setSize(200, 200);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
