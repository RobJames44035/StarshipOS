/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.awt.Dialog;
import java.awt.Frame;

/*
 * @test
 * @bug 4964237
 * @requires (os.family == "windows")
 * @summary Win: Changing theme changes java dialogs title icon
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual DefaultIconTest
 */

public class DefaultIconTest {
    static String instructions = """
                    This test shows frame and two dialogs
                    Change windows theme. Resizable dialog should retain default icon
                    Non-resizable dialog should retain no icon
                    Press PASS if icons look correct, FAIL otherwise
                    """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("ShownModalDialogSerializationTest Instructions")
                .instructions(instructions)
                .testTimeOut(5)
                .rows(10)
                .columns(35)
                .testUI(DefaultIconTest::createGUIs)
                .build()
                .awaitAndCheck();
    }

    public static Frame createGUIs() {
        Frame f = new Frame("DefaultIconTest");
        f.setSize(200, 100);
        Dialog d1 = new Dialog(f, "Resizable Dialog, should show default icon");
        d1.setSize(200, 100);
        d1.setVisible(true);
        d1.setLocation(0, 150);
        Dialog d2 = new Dialog(f, "Non-resizable dialog, should have no icon");
        d2.setSize(200, 100);
        d2.setVisible(true);
        d2.setResizable(false);
        d2.setLocation(0, 300);
        return f;
    }
}
