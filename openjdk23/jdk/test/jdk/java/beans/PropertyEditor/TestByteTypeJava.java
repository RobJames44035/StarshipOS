/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596
 * @summary Tests PropertyEditor for value of type byte
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestByteTypeJava {
    public static void main(String[] args) {
        new TestEditor(Byte.TYPE).testJava(Byte.valueOf((byte) 12));
    }
}
