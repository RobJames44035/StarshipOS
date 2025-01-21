/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @summary Verify no exception for unsupported code point.
 * @bug 8172967
 */
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class MissingCodePointLayoutTest {
    public static void main(String[] args) {
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        String text = "\ude00";
        FontRenderContext frc = new FontRenderContext(null, false, false);
        TextLayout layout = new TextLayout(text, font, frc);
        layout.getCaretShapes(0);
    }
}

