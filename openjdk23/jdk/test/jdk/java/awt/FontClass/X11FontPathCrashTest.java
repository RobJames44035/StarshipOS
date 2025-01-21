/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6958221
 * @summary Test no crashing getting fonts on X11
 * @run main X11FontPathCrashTest
 */

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;


public class X11FontPathCrashTest {
    public static void main(String[] args) {
        new Font("nonexistentfont", Font.PLAIN, 12).getFamily();
    }
}

