/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6498171 6538853
 * @summary Tests PropertyEditor for null value of type Font
 * @author Sergey Malenkov
 * @key headful
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

import java.awt.Font;

public class TestFontClassNull {
    public static void main(String[] args) {
        new TestEditor(Font.class).testJava(null);
    }
}
