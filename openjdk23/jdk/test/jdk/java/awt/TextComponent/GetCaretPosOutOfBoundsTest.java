/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */
/*
  @test
  @bug 4995931
  @summary java.awt.TextComponent caret position should be within the text bounds
  @key headful
*/

import java.awt.EventQueue;
import java.awt.TextField;

public class GetCaretPosOutOfBoundsTest {
    static TextField tf;
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            tf = new TextField("1234567890");
            tf.setCaretPosition(100);
            int pos = tf.getCaretPosition();
            if (pos > 10) {
                throw new RuntimeException("Wrong caret position:" + pos + " instead of 10");
            }
            tf.setText("12345");
            if (tf.getCaretPosition() > 5) {
                throw new RuntimeException("Wrong caret position:" + pos + " instead of 5");
            }
        });
    }
}
