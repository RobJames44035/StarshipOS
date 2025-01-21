/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4461329
 * @summary Tests getPreviewPanel() and setPreviewPanel() methods
 * @author Leif Samuelsson
 */

import javax.swing.JButton;
import javax.swing.JColorChooser;

public class Test4461329 {
    public static void main(String[] args) {
        JColorChooser chooser = new JColorChooser();
        if (null == chooser.getPreviewPanel()) {
            throw new Error("Failed: getPreviewPanel() returned null");
        }
        JButton button = new JButton("Color"); // NON-NLS: simple label
        chooser.setPreviewPanel(button);
        if (button != chooser.getPreviewPanel()) {
            throw new Error("Failed in setPreviewPanel()");
        }
    }
}
