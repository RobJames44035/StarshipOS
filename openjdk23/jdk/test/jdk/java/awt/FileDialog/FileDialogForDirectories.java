/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * @test
 * @bug 7161437
 * @summary We should support "apple.awt.fileDialogForDirectories" property.
 * @requires (os.family == "mac")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FileDialogForDirectories
 */

public class FileDialogForDirectories {

    private static JFrame initialize() {
        System.setProperty("apple.awt.fileDialogForDirectories", "true");

        JFrame frame = new JFrame("Directory File Dialog Test Frame");
        frame.setLayout(new BorderLayout());
        JTextArea textOutput = new JTextArea(8, 30);
        textOutput.setLineWrap(true);
        JScrollPane textScrollPane = new JScrollPane(textOutput);
        frame.add(textScrollPane, BorderLayout.CENTER);

        FileDialog fd = new FileDialog(new Frame(), "Open");

        Button showBtn = new Button("Show File Dialog");
        showBtn.addActionListener(e -> {
            fd.setVisible(true);
            String output = fd.getFile();
            if (output != null) {
                textOutput.append(output + " is selected\n");
                textOutput.setCaretPosition(textOutput.getText().length());
            }
        });
        frame.add(showBtn, BorderLayout.NORTH);
        frame.pack();
        return frame;
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        String instructions = """
                1) Click on 'Show File Dialog' button. A file dialog will come up.
                2) Check that files can't be selected.
                3) Check that directories can be selected.
                4) Repeat steps 1 - 3 a few times for different files and directories.
                5) If it's true then the press Pass, otherwise press Fail.
                """;

        PassFailJFrame.builder()
                .title("Directory File Dialog Test Instructions")
                .instructions(instructions)
                .rows((int) instructions.lines().count() + 1)
                .columns(40)
                .testUI(FileDialogForDirectories::initialize)
                .build()
                .awaitAndCheck();
    }
}
