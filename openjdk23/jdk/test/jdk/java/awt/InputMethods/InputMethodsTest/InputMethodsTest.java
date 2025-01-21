/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;

/*
 * @test
 * @bug 7146572 8024122
 * @summary Check if 'enableInputMethods' works properly for TextArea and TextField on Linux platform
 * @requires (os.family == "linux")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual InputMethodsTest
*/

public class InputMethodsTest {

    private static final String INSTRUCTIONS = """
        Test run requires some Japanese input method to be installed.

        To test the JDK-7146572 fix, please follow these steps:
        1. Enable the input method.
        2. Type Japanese in the text area and the text field to the right.
        2. Press the "Disable Input Methods" button.
        3. Try typing Japanese again.
        4. If input methods are not disabled, then press fail;
           otherwise, press pass.
        """;

    static boolean inputMethodsEnabled = true;

    public static void main(String[] args) throws Exception {
        PassFailJFrame
                .builder()
                .title("InputMethodsTest Instructions")
                .instructions(INSTRUCTIONS)
                .splitUIRight(InputMethodsTest::createPanel)
                .testTimeOut(10)
                .rows(10)
                .columns(40)
                .build()
                .awaitAndCheck();
    }

    public static JComponent createPanel() {
        Box verticalBox = Box.createVerticalBox();

        TextArea textArea = new TextArea();
        verticalBox.add(textArea);

        TextField textField = new TextField();
        verticalBox.add(textField);

        JButton btnIM = new JButton();
        setBtnText(btnIM);

        btnIM.addActionListener(e -> {
            inputMethodsEnabled = !inputMethodsEnabled;
            setBtnText(btnIM);
            textArea.enableInputMethods(inputMethodsEnabled);
            textField.enableInputMethods(inputMethodsEnabled);
        });

        verticalBox.add(btnIM);
        return verticalBox;
    }

    private static void setBtnText(JButton btnIM) {
        String s = inputMethodsEnabled ? "Disable" : "Enable";
        btnIM.setText(s +  " Input Methods");
    }
}

