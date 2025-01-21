/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7002839
 * @summary Static init deadlock Win32GraphicsEnvironment and WToolkit
 * @run main GE_init2
 */


import java.awt.GraphicsEnvironment;

public class GE_init2 {
    public static void main(String[] args) {
        GraphicsEnvironment.getLocalGraphicsEnvironment();
    }
}
