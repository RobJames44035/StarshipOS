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

public class TestEnumSubclassValue {
    public static void main(String[] args) {
        TestEditor test = new TestEditor(Operation.class);
        test.testValue(Operation.PLUS, "PLUS");
        test.testValue(null, null);
        test.testText("MINUS", Operation.MINUS);
        test.testText(null, null);
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
