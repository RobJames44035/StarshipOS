/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4222827 4506596 6498158
 * @summary Tests PropertyEditor for value of type Boolean
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestBooleanClassValue {
    public static void main(String[] args) {
        TestEditor test = new TestEditor(Boolean.class);
        test.testValue(true, "True");
        test.testValue(null, null);
        test.testText("False", false);
        test.testText(null, null);
    }
}
