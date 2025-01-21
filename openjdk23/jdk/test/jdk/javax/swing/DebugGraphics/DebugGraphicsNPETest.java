/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.awt.Font;
import javax.swing.DebugGraphics;

/* @test
 * @bug 6521141
 * @summary Test to check if NPE does not occur when graphics is not
 *  initialized and DebugGraphics instance is created with default
 *  Constructor and used.
 * @run main DebugGraphicsNPETest
 */
public class DebugGraphicsNPETest {
    public static void main(String[] args) throws Exception {
        DebugGraphics dg = new DebugGraphics();
        Font font = new Font(Font.SERIF, Font.PLAIN, 10);
        dg.setFont(font);
        System.out.println("Test Pass!");
    }
}
