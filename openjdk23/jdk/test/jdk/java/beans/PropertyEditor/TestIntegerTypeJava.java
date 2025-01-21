/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596
 * @summary Tests PropertyEditor for value of type int
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestIntegerTypeJava {
    public static void main(String[] args) {
        new TestEditor(Integer.TYPE).testJava(Integer.valueOf(12));
    }
}
