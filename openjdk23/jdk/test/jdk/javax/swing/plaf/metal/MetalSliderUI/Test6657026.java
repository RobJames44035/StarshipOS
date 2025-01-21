/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6657026 7077259
 * @summary Tests shared MetalSliderUI in different application contexts
 * @author Sergey Malenkov
 * @modules java.desktop/sun.awt
 * @run main/othervm -Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel Test6657026
 */

import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalSliderUI;
import sun.awt.SunToolkit;

public class Test6657026 extends MetalSliderUI implements Runnable {

    public static void main(String[] args) throws Exception {
        JSlider slider = new JSlider();
        test(slider);

        ThreadGroup group = new ThreadGroup("$$$");
        Thread thread = new Thread(group, new Test6657026());
        thread.start();
        thread.join();

        test(slider);
    }

    public void run() {
        SunToolkit.createNewAppContext();
        JSlider slider = new JSlider();
        test(slider);
        tickLength = -10000;
    }

    private static void test(JSlider slider) {
        MetalSliderUI ui = (MetalSliderUI) slider.getUI();
        int actual = ui.getTickLength();
        if (actual != 11) {
            throw new Error(actual + ", but expected 11");
        }
    }
}
