 /*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

 import javax.swing.JEditorPane;
 import javax.swing.SwingUtilities;

 /*
  * @test
  * @bug 4330998
  * @summary Verifies that JEditorPane.setText(null) doesn't throw NullPointerException.
  * @run main JEditorPaneSetTextNullTest
  */
 public class JEditorPaneSetTextNullTest {

     public static void main(String[] args) throws Exception {
         try {
             SwingUtilities.invokeAndWait(() -> new JEditorPane().setText(null));
             System.out.println("Test passed");
         } catch (Exception e) {
             throw new RuntimeException("Test failed, caught Exception " + e
                     + " when calling JEditorPane.setText(null)");
         }
     }

 }
