/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4222827 4506596
 * @summary Tests PropertyEditor for value of type byte
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestByteTypeValue {
    public static void main(String[] args) {
        TestEditor test = new TestEditor(Byte.TYPE);
        test.testValue((byte) 0, "0");
        test.testValue(null, null);
        test.testText("1", (byte) 1);
        test.testText(null, null);
    }
}
