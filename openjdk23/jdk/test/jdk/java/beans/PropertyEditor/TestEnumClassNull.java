/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 4506596 6219769 6498171
 * @summary Tests PropertyEditor for null value of type Enum
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestEnumClassNull {
    public static void main(String[] args) {
        new TestEditor(HexLetter.class).testJava(null);
    }

    public enum HexLetter {A,B,C,D,E,F}
}
