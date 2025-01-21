/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6498158 6498171
 * @summary Tests PropertyEditor for null value of type Integer
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestIntegerClassNull {
    public static void main(String[] args) {
        new TestEditor(Integer.class).testJava(null);
    }
}
