/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4587721
 * @summary Tests if JFileChooser details view chops off text
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual bug4587721
 */

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JFileChooser;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class bug4587721 {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new MetalLookAndFeel());

        String instructions = """
                Click on the Details button in JFileChooser Window.
                If the filename text is chopped off by height,
                then Press FAIL else Press PASS.
                """;

        PassFailJFrame.builder()
                .title("bug4587721")
                .instructions(instructions)
                .columns(40)
                .testUI(bug4587721::createUI)
                .build()
                .awaitAndCheck();
    }

    public static JFileChooser createUI() {
        setFonts();
        JFileChooser fc = new JFileChooser();
        return fc;
    }

    public static void setFonts() {
        UIDefaults defaults = UIManager.getDefaults();
        Enumeration keys = defaults.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (defaults.get(key) instanceof Font)
                UIManager.put(key, new FontUIResource(new Font("Courier", Font.BOLD, 30)));
        }
    }
}
