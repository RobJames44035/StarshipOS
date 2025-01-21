/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
import javax.swing.SwingUtilities;
import javax.swing.JTextField;

/*
 * @test
 * @bug 8037965
 * @summary Verifies NPE in TextLayout.getBaselineFromGraphic() for invalid
 *          Unicode characters
 */
public class SwingUnicodeTest {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() ->
            new JTextField(new StringBuilder().appendCodePoint(0xFFFF).
                            appendCodePoint(0x10000).toString()));
    }
}
