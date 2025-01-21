/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/*
 * @test
 * @bug 8291792
 * @key headful
 * @summary Test to check if negative length check is implemented in
 * setCharacterAttributes(). Test should not throw any exception on
 * negative length.
 * @run main DocNegLenCharAttrTest
 */
public class DocNegLenCharAttrTest {
    private static JFrame frame;
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    test();
                }
            });
        } finally {
            frame.dispose();
        }
        System.out.println("Test Pass!");
    }

    public static void test() {
        DefaultStyledDocument doc;
        frame = new JFrame();
        doc = new DefaultStyledDocument();
        JTextPane text = new JTextPane();
        text.setDocument(doc);
        text.setText("hello world");
        doc.setCharacterAttributes(6, -5,
                createLabelAttribute("world"), true);

        frame.setPreferredSize(new Dimension(100,70));
        frame.add(text);
        frame.setLayout(new BorderLayout());
        frame.add(text,BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.pack();
    }

    private static AttributeSet createLabelAttribute(String text){
        JLabel lbl = new JLabel(text.toUpperCase());
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setComponent(attr,lbl);
        return attr;
    }
}
