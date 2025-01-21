/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6736248
 * @summary Tests PropertyEditor for value of subtype Enum
 * @author Sergey Malenkov
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 */

public class TestEnumSubclassJava {
    public static void main(String[] args) {
        new TestEditor(Operation.class).testJava(Operation.PLUS);
    }

    public enum Operation {
        PLUS {
            public int run(int i, int j) {
                return i + j;
            }
        },
        MINUS {
            public int run(int i, int j) {
                return i - j;
            }
        };
        public abstract int run(int i, int j);
    }
}
