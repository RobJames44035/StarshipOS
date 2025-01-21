/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/**
 * @test
 * @bug 4931668
 * @summary Tests XEmbed server/client functionality
 * @author denis mikhalkin: area=awt.xembed
 * @requires (!(os.family=="mac") & !(os.family=="windows"))
 * @modules java.desktop/sun.awt
 * @compile JavaClient.java TesterClient.java TestXEmbedServer.java
 * @run main/manual TestXEmbedServerJava
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class TestXEmbedServerJava extends TestXEmbedServer {
    public static void main(String[] args) {

        // Enabled XEmbed
        System.setProperty("sun.awt.xembedserver", "true");

        String instruction =
            "This is a manual test for XEmbed server functionality. \n" +
            "You may start XEmbed client by pressing 'Add client' button.\n" +
            "Check that focus transfer with mouse works, that focus traversal with Tab/Shift-Tab works.\n" +
            "Check that XEmbed server client's growing and shrinking.\n" +
            "Check that Drag&Drop works in all combinations.\n" +
            "Check the keyboard input works in both text fields.\n";
        Frame f = new Frame("Instructions");
        f.setLayout(new BorderLayout());
        f.add(new TextArea(instruction), BorderLayout.CENTER);
        f.pack();
        f.setLocation(0, 400);
        f.setVisible(true);

        TestXEmbedServerJava lock = new TestXEmbedServerJava();
        try {
            synchronized(lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
        }
        if (!lock.isPassed()) {
            throw new RuntimeException("Test failed");
        }
    }

    public TestXEmbedServerJava() {
        super(true);
    }

    public Process startClient(Rectangle[] bounds, long window) {
        try {
            String java_home = System.getProperty("java.home");
            boolean hasModules = true;
            try {
                Class.class.getMethod("getModule");
            }catch(Exception hasModulesEx) {
                hasModules = false;
            }
            if (hasModules) {
                System.out.println(java_home +
                               "/bin/java --add-exports java.desktop/sun.awt.X11=ALL-UNNAMED "+
                               "--add-exports java.desktop/sun.awt=ALL-UNNAMED  JavaClient " + window);
                return Runtime.getRuntime().exec(java_home +
                               "/bin/java --add-exports java.desktop/sun.awt.X11=ALL-UNNAMED "+
                               "--add-exports java.desktop/sun.awt=ALL-UNNAMED  JavaClient " + window);
            }else{
                System.out.println(java_home + "/bin/java JavaClient " + window);
                return Runtime.getRuntime().exec(java_home + "/bin/java JavaClient " + window);
            }
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return null;
    }
}
