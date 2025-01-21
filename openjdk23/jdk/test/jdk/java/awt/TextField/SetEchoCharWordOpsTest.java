/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JPanel;

import jdk.test.lib.Platform;

/*
 * @test
 * @bug 6191897
 * @summary Verifies that ctrl+left/right does not move word-by-word in a TextField
 *          with echo character set
 * @library /java/awt/regtesthelpers  /test/lib
 * @build PassFailJFrame jdk.test.lib.Platform
 * @run main/manual SetEchoCharWordOpsTest
 */

public class SetEchoCharWordOpsTest {

    public static void main(String[] args) throws Exception {
        String selectAllKey;
        String moveKeys;
        String selectKeys;

        if (Platform.isOSX()) {
            selectAllKey = "Cmd + A";
            moveKeys = "Alt + Right/Left";
            selectKeys = "Shift + Alt + Right/Left";
        } else {
            selectAllKey = "Ctrl + A";
            moveKeys = "Ctrl + Right/Left";
            selectKeys = "Shift + Ctrl + Right/Left";
        }

        String instructions =
                "The password field (in the bottom panel) in this test contains"
                 + " a few words (3 words).\n"
                 + "Move the focus to the text field and press " + selectAllKey + ".\n"
                 + "Try moving the caret word-by-word with " + moveKeys + " or"
                 + " extending selection with " + selectKeys + "."
                 + " You should NOT be able to do that.\n\n"
                 + "If you are able to move the caret word-by-word press FAIL,"
                 + " else press PASS.";

        PassFailJFrame.builder()
                      .title("SetEchoCharClipboard Instructions")
                      .instructions(instructions)
                      .rows((int) instructions.lines().count() + 3)
                      .columns(45)
                      .splitUIBottom(SetEchoCharWordOpsTest::createAndShowUI)
                      .build()
                      .awaitAndCheck();
    }


    private static JPanel createAndShowUI() {
        JPanel jPanel = new JPanel();
        TextField tf = new TextField("one two three", 15);
        Label tfLabel = new Label("Password Field:");

        jPanel.setLayout(new FlowLayout());
        tf.setEchoChar('*');
        jPanel.add(tfLabel);
        jPanel.add(tf);
        return jPanel;
    }
}
