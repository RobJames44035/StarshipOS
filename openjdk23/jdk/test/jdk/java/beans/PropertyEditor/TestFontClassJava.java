/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6538853
 * @summary Tests PropertyEditor for value of type Font
 * @author Sergey Malenkov
 * @key headful
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

import java.awt.Font;

public class TestFontClassJava {
    public static void main(String[] args) {
        new TestEditor(Font.class).testJava(new Font("Helvetica", Font.ITALIC, 12));
    }
}
