/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
 * @bug 4252173 7077259
 * @summary Inability to reuse the HorizontalSliderThumbIcon
 * @author Pavel Porvatov
 * @run main/othervm -Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel bug4252173
 */

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

public class bug4252173 {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());

                JComponent component = new JLabel();

                Icon horizontalThumbIcon = UIManager.getIcon("Slider.horizontalThumbIcon");

                Icon verticalThumbIcon = UIManager.getIcon("Slider.verticalThumbIcon");

                Graphics g = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR).getGraphics();

                horizontalThumbIcon.paintIcon(component, g, 0, 0);

                verticalThumbIcon.paintIcon(component, g, 0, 0);
            }
        });
    }
}
