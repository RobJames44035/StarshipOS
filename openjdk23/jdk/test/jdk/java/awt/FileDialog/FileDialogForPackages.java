/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.awt.FileDialog;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;

/*
 * @test
 * @bug 8026869
 * @summary Support apple.awt.use-file-dialog-packages property.
 * @requires (os.family == "mac")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FileDialogForPackages
*/

public class FileDialogForPackages {

    private static JButton initialize() {
        System.setProperty("apple.awt.use-file-dialog-packages", "true");

        FileDialog fd = new FileDialog((Frame) null, "Open");
        String APPLICATIONS_FOLDER = "/Applications";
        fd.setDirectory(APPLICATIONS_FOLDER);

        JButton showBtn = new JButton("Show File Dialog");
        showBtn.addActionListener(e -> {
            fd.setVisible(true);
            String output = fd.getFile();
            if (output != null) {
                PassFailJFrame.log(output + " is selected\n");
            }
        });
        return showBtn;
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        String instructions = """
                1) Click on 'Show File Dialog' button. A file dialog will come up.
                2) Navigate to the Applications folder if not already there.
                3) Check that the application bundles can be selected
                   but can not be navigated.
                4) If it's true then press Pass, otherwise press Fail.
                """;

        PassFailJFrame.builder()
                .title("Directory File Dialog Test Instructions")
                .instructions(instructions)
                .rows((int) instructions.lines().count() + 1)
                .columns(40)
                .logArea(8)
                .splitUIBottom(FileDialogForPackages::initialize)
                .build()
                .awaitAndCheck();
    }
}
