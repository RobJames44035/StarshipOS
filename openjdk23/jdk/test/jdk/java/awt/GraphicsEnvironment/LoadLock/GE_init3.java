/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 7002839
 * @summary Static init deadlock Win32GraphicsEnvironment and WToolkit
 * @run main GE_init3
 */


import java.awt.Frame;

public class GE_init3 {
    public static void main(String[] args) {
        new Frame("Test3").setVisible(true);
    }
}
