/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4222827 4506596
 * @summary Tests PropertyEditor for value of type Short
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestShortClassValue {
    public static void main(String[] args) {
        TestEditor test = new TestEditor(Short.class);
        test.testValue((short) 0, "0");
        test.testValue(null, null);
        test.testText("1", (short) 1);
        test.testText(null, null);
    }
}
