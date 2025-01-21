/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
/*
 * @test
 * @bug 8042055
 * @summary  Verifies Nimbus DerivedColor clamps hue correctly
 * @run main DerivedColorHueTest
 */

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Explanation: We get a color from the Nimbus L&F and hue-shift it in two
 * different ways:
 * 1. Using the Nimbus getDerivedColor method.
 * 2. Manually using AWT's Color.RGBtoHSB / HSBtoRGB.
 * Since the hue spectrum is cyclic, a hue less than 0 or larger than 1 should
 * wrap around to stay within this range. This is what Color.HSBtoRGB does.
 * The bug is that the Nimbus getDerivedColor method clamps the value, so that
 * large hue shifts cause any hue to become 0 (red) or 1 (also red).
 */
public class DerivedColorHueTest {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        Color base = UIManager.getColor("nimbusBlueGrey");
        float[] hsbBase = hsb(base);
        float hueShift = 0.5f - 10; // magnitude bigger than 1 to ensure it cycles

        Color derived = ((NimbusLookAndFeel)UIManager.getLookAndFeel())
                         .getDerivedColor("nimbusBlueGrey", hueShift, 0, 0, 0, false);
        Color derivedCorrect = new Color(
                   Color.HSBtoRGB(hsbBase[0] + hueShift, hsbBase[1], hsbBase[2]));

        float hueDerived = hsb(derived)[0];
        float hueCorrect = hsb(derivedCorrect)[0];

        if (hueCorrect < 0.01f || hueCorrect > 0.99f)
            throw new RuntimeException("Test indeterminate! (Hue too close to red)");

        System.out.println(" base: " + hsbString(base));
        System.out.println(" derived: " + hsbString(derived));
        System.out.println("derivedCorrect: " + hsbString(derivedCorrect));

        if (Math.abs(hueDerived - hueCorrect) < 0.001f) {
            System.out.println("[PASS]");
        } else {
            throw new RuntimeException("Nimbus derived hue color is not correct");
        }
    }

    private static float[] hsb(Color c) {
        return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
    }

    private static String hsbString(Color c) {
        float[] hsb = hsb(c);
        return String.format("H=%.2f, S=%.2f, B=%.2f", hsb[0], hsb[1], hsb[2]);
    }
}
