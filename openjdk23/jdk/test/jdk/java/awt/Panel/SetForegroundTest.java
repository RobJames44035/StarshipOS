/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
  @test
  @bug 4994151
  @summary REGRESSION: Bug when setting the foreground of a JWindow
  @key headful
*/

import java.awt.EventQueue;
import java.awt.Color;

import javax.swing.JWindow;

public class SetForegroundTest {
    static JWindow jwindow;

    public static void main(String[] args) throws Exception {
        try {
            EventQueue.invokeAndWait(() -> {
                jwindow = new JWindow();
                jwindow.pack();
                jwindow.setForeground(Color.BLACK);
                System.out.println("TEST PASSED");
            });
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (jwindow != null) {
                    jwindow.dispose();
                }
            });
        }
    }
}
