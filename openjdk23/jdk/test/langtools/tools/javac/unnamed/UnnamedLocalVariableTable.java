/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8313323
 * @summary Verify javac does not produce incorrect LocalVariableTable
 * @enablePreview
 * @compile -g UnnamedLocalVariableTable.java
 * @run main UnnamedLocalVariableTable
 */
public class UnnamedLocalVariableTable {
    public static void main(String... args) {
        try {
            int _ = 0;
            if (args[0] instanceof String _) {
                System.err.println("1");
            }
            I i = _ -> {};
            java.util.List<String> _ = null;
        } catch (Exception _) {
            System.err.println("2");
        }
    }

    interface I {
        public void test(String s);
    }
}
