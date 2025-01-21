/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/* @test
 * @bug 8269223
 * @summary Verifies that -Xcheck:jni issues no warnings from freetypeScaler.c
 * @library /test/lib
 * @run main FreeTypeScalerJNICheck
 */
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class FreeTypeScalerJNICheck {
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("runtest")) {
            runTest();
        } else {
            ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-Xcheck:jni", FreeTypeScalerJNICheck.class.getName(), "runtest");
            OutputAnalyzer oa = ProcessTools.executeProcess(pb);
            oa.shouldContain("Done")
                .shouldNotContain("WARNING")
                .shouldNotContain("AWT Assertion")
                .shouldHaveExitValue(0);
        }
    }

    public static void runTest() {
        String families[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();

        for (String ff : families) {
            Font font = new Font(ff, Font.PLAIN, 12);
            Rectangle2D bounds = font.getStringBounds("test", g2d.getFontRenderContext());
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font);
            System.out.println(bounds.getHeight() + metrics.getHeight()); // use bounds and metrics
        }

        System.out.println("Done");
    }
}
