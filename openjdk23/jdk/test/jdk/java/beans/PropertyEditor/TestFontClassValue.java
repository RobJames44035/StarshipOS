/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4222827 4506596 6538853
 * @summary Tests PropertyEditor for value of type Font
 * @author Sergey Malenkov
 * @key headful
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

import java.awt.Font;

public class TestFontClassValue {
    public static void main(String[] args) {
        TestEditor test = new TestEditor(Font.class);
        test.testValue(new Font("Helvetica", Font.BOLD | Font.ITALIC, 20), "Helvetica BOLDITALIC 20");
        test.testValue(null, null);
        test.testText("Helvetica 12", new Font("Helvetica", Font.PLAIN, 12));
        test.testText(null, null);
    }
}
