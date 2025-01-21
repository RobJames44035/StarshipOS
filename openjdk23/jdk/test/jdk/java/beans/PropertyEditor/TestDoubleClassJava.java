/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596
 * @summary Tests PropertyEditor for value of type Double
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestDoubleClassJava {
    public static void main(String[] args) {
        new TestEditor(Double.class).testJava(Double.valueOf(12.34));
    }
}
