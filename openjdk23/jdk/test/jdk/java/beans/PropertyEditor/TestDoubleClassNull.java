/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6498171
 * @summary Tests PropertyEditor for null value of type Double
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestDoubleClassNull {
    public static void main(String[] args) {
        new TestEditor(Double.class).testJava(null);
    }
}
