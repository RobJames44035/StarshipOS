/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4694598
 * @key headful
 * @summary JEditor pane throws NullPointerException on mouse movement.
 * @run main bug4694598
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class bug4694598 {
    JFrame frame;
    volatile int bottom;
    final Path frameContentFile = Path.of("FrameContent.html");

    public void setupGUI() {
        frame = new JFrame("Test 4694598");
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        frame.getContentPane().add(jep);
        frame.setLocation(50, 50);
        frame.setSize(400, 400);

        String frameContentString = "<HTML><BODY>\n" +
                "    Frame content.\n" +
                "</BODY></HTML>";
        try (Writer writer = Files.newBufferedWriter(frameContentFile)) {
            writer.write(frameContentString);
        } catch (IOException ioe){
            throw new RuntimeException("Could not create html file to embed", ioe);
        }
        URL frameContentUrl;
        try {
            frameContentUrl = frameContentFile.toUri().toURL();
        } catch (MalformedURLException mue) {
            throw new RuntimeException("Can not convert path to URL", mue);
        }
        jep.setContentType("text/html");
        String html = "<HTML> <BODY>" +
                "<FRAMESET cols=\"100%\">" +
                "<FRAME src=\"" + frameContentUrl + "\">" +
                "</FRAMESET>" +
                // ! Without <noframes> bug is not reproducable
                "<NOFRAMES>" +
                "</NOFRAMES>" +
                "</BODY> </HTML>";
        jep.setText(html);

        frame.setVisible(true);
    }

    public void performTest() throws InterruptedException,
            InvocationTargetException, AWTException {
        Robot robo = new Robot();
        robo.waitForIdle();

        final int range = 20;
        SwingUtilities.invokeAndWait(() -> {
            bottom = frame.getLocationOnScreen().y
                    + frame.getSize().height - range;
        });
        for (int i = 0; i < range; i++) {
            robo.mouseMove(300, bottom + i);
            robo.waitForIdle();
            robo.delay(50);
        }
    }

    public void cleanupGUI() {
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
        }
        try {
            Files.deleteIfExists(frameContentFile);
        } catch (IOException ignore) {}
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException, AWTException {
        bug4694598 app = new bug4694598();
        SwingUtilities.invokeAndWait(app::setupGUI);
        app.performTest();
        SwingUtilities.invokeAndWait(app::cleanupGUI);
    }
}
