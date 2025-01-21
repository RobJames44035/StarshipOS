/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4222827 4506596
 * @summary Tests PropertyEditor for value of type Long
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestLongClassValue {
    public static void main(String[] args) {
        TestEditor test = new TestEditor(Long.class);
        test.testValue(0l, "0");
        test.testValue(null, null);
        test.testText("1", 1l);
        test.testText(null, null);
    }
}
