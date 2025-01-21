/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
   @bug 6990651
   @summary Regression: NPE when refreshing applet since 6u22-b01
   @author Pavel Porvatov
   @modules java.desktop/sun.awt
*/
import sun.awt.SunToolkit;

import javax.swing.*;

public class bug6990651 {
    private static volatile JEditorPane editor;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                editor = new JEditorPane("text/html", "Hello world!");
            }
        });

        Thread thread = new Thread(new ThreadGroup("Some ThreadGroup"), new Runnable() {
            public void run() {
                SunToolkit.createNewAppContext();

                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            editor.setText("Hello world!");
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        thread.start();
        thread.join();
    }
}
