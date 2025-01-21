/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

/*
 * @test
 * @bug 8301989
 * @summary Test checks that there is no exception happens
 *          when setting blink rate on a javax.swing.DefaultCaret that is not
 *          associated with any text component
 * @run main SetCaretRateTest
 */
public class SetCaretRateTest {
    public static void main(String[] args) {
        Caret caret = new DefaultCaret();
        caret.setBlinkRate(0);
        caret.setBlinkRate(100);
        caret.setBlinkRate(0);
        caret = new DefaultCaret();
        caret.setBlinkRate(100);
        caret.setBlinkRate(0);
        caret.setBlinkRate(100);
    }
}
