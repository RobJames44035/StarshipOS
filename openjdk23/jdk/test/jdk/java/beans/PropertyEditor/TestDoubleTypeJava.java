/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596
 * @summary Tests PropertyEditor for value of type double
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestDoubleTypeJava {
    public static void main(String[] args) {
        new TestEditor(Double.TYPE).testJava(Double.valueOf(12.34));
    }
}
