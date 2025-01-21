/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.util.Date;
import java.util.ResourceBundle;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
  * This test is mean to isolate the speed of the JTextArea
  * It creates a JTextArea and then continuously appends text
  * to that string.
  */

public class TextAreaTest extends AbstractSwingTest {

   JTextArea textArea1;
   final int repeat = 300;
   final int breakIncrement = 12;

   String DISPLAY_STRING = "Swing is Fast!  ";

   public JComponent getTestComponent() {
       loadBundle();
       JPanel panel = new JPanel();
       textArea1 = new CountTextArea(10, 30);
       textArea1.setLineWrap(true);
       JScrollPane scroller = new JScrollPane(textArea1);

       if (SwingMark.useBlitScrolling) {
           scroller.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
       }

       panel.add(scroller);
       return panel;
   }

   public String getTestName() {
       return "TextArea";
   }

   public void runTest() {
       testTextArea(textArea1, DISPLAY_STRING);
   }

   private void loadBundle() {
       ResourceBundle bundle = ResourceBundle.getBundle("resources.TextAreaTest");
       DISPLAY_STRING = bundle.getString("DisplayString");
   }


   public void testTextArea(JTextArea currentTextArea, String appendThis) {

       TextAppender appender = new TextAppender(currentTextArea, appendThis);
       for (int i = 0; i < repeat; i++) {
          appender.appendString = appendThis;
          if ( i % breakIncrement == breakIncrement -1) {
             appender.appendString = appendThis + "\n";
          }
          try {
              SwingUtilities.invokeLater(appender);
              rest();
          } catch (Exception e) {System.out.println(e);}
       }
   }

   public static void main(String[] args) {
       runStandAloneTest(new TextAreaTest());
   }

   class CountTextArea extends JTextArea {

       public CountTextArea(int h, int w) {
           super(h, w);
       }

       public void paint(Graphics g) {
           super.paint(g);
           paintCount++;
       }
   }
}

class TextAppender implements Runnable {

    JTextArea area;
    String appendString;

    public TextAppender(JTextArea textArea, String appendThis) {
        area = textArea;
        appendString = appendThis;
    }

    public void run() {
        area.append(appendString);
    }
}
