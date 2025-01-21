/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596
 * @summary Tests PropertyEditor for value of type float
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestFloatTypeJava {
    public static void main(String[] args) {
        new TestEditor(Float.TYPE).testJava(Float.valueOf(12.34f));
    }
}
