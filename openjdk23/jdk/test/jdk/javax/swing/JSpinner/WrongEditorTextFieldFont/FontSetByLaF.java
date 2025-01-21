/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static javax.swing.UIManager.getInstalledLookAndFeels;

/**
 * @test
 * @key headful
 * @bug 8205144
 * @summary Verify font of the text field is changed to the font of
 *          JSpinner if the font of text field was NOT set by the user and look
 *          and feel tried to set it to default font of the TextUI
 */
public final class FontSetByLaF {

    public static void main(final String[] args) throws Exception {
        for (final UIManager.LookAndFeelInfo laf : getInstalledLookAndFeels()) {
            EventQueue.invokeAndWait(() -> setLookAndFeel(laf));
            EventQueue.invokeAndWait(() -> {

                JSpinner spinner = new JSpinner();
                DefaultEditor editor = (DefaultEditor) spinner.getEditor();
                JFormattedTextField textField = editor.getTextField();

                Font before = textField.getFont();
                SwingUtilities.updateComponentTreeUI(spinner);
                Font after = textField.getFont();

                if (!before.equals(after)) {
                    System.err.println("Before: " + before);
                    System.err.println("After: " + after);
                    throw new RuntimeException();
                }
            });
        }
    }

    private static void setLookAndFeel(final UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
            System.err.println("LookAndFeel: " + laf.getClassName());
        } catch (final UnsupportedLookAndFeelException ignored) {
            System.err.println(
                    "Unsupported LookAndFeel: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
