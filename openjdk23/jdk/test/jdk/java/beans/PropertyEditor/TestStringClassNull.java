/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6457659 6498171
 * @summary Tests PropertyEditor for null value of type String
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestStringClassNull {
    public static void main(String[] args) {
        new TestEditor(String.class).testJava(null);
    }
}
