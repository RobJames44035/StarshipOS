/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
/*
 * @test
 * @bug 6753661 8302882
 * @key headful
 * @summary  Verifies if JFileChooser font reset after Look & Feel change
 * @run main JFileChooserFontReset
 */
import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

public class JFileChooserFontReset {
    private static void setLookAndFeel(UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported L&F: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            for (UIManager.LookAndFeelInfo laf :
                     UIManager.getInstalledLookAndFeels()) {
                System.out.println("Testing L&F: " + laf.getClassName());
                setLookAndFeel(laf);
                JFileChooser fc = new JFileChooser();
                Font origFont = fc.getFont();
                System.out.println(" orig font " + origFont);
                for (UIManager.LookAndFeelInfo newLaF :
                    UIManager.getInstalledLookAndFeels()) {
                    if (laf.equals(newLaF)) {
                        continue;
                    }
                    System.out.println("Transition to L&F: " + newLaF);
                    setLookAndFeel(newLaF);
                    SwingUtilities.updateComponentTreeUI(fc);
                    setLookAndFeel(laf);
                    System.out.println("Back to L&F: " + laf);
                    SwingUtilities.updateComponentTreeUI(fc);
                    Font curFont = fc.getFont();
                    System.out.println("current font " + curFont);
                    if (curFont != origFont
                        && (curFont != null && !curFont.equals(origFont))) {
                        throw new RuntimeException(
                         "JFileChooser font is not reset after Look & Feel change");
                    }
                }
                System.out.println("");
                System.out.println("");
            }
        });
    }
}

