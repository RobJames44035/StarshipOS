/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.util.Date;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TypingTest extends AbstractSwingTest {

    JTextArea textArea1;
    final int repeat = 100;

    public JComponent getTestComponent() {
        JPanel panel = new JPanel();
        textArea1 = new JTextArea(10, 30);
        textArea1.setLineWrap(true);
        JScrollPane scroller = new JScrollPane(textArea1);
        panel.add(scroller);
        return panel;
    }

    public boolean canRunInApplet() {
        return false;
    }

    public String getTestName() {
        return "Typing";
    }

    public void runTest() {
        testTyping(textArea1, "Write once, run anywhere!  ");
    }

    public void testTyping(JTextArea currentTextArea, String stuff) {
        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();

        int n = stuff.length();
        for (int i = 0; i < repeat; i++)
            for (int j = 0; j < n; j++) {
                char c = stuff.charAt(j);
                KeyEvent key = new KeyEvent(currentTextArea,
                                    KeyEvent.KEY_TYPED, new Date().getTime(),
                                    0, KeyEvent.VK_UNDEFINED, c);
                queue.postEvent(key);
                rest();
        }
    }

    public static void main(String[] args) {
        runStandAloneTest(new TypingTest());
    }
}
