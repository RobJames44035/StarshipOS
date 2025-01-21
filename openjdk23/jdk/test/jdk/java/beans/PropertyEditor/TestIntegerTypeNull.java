/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6498171
 * @summary Tests PropertyEditor for null value of type int
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestIntegerTypeNull {
    public static void main(String[] args) {
        new TestEditor(Integer.TYPE).testJava(null);
    }
}
