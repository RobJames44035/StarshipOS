/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8297556
 * @summary Parse::check_interpreter_type fails with assert "must constrain OSR typestate"
 *
 * @run main/othervm -Xbatch -XX:-TieredCompilation -XX:CompileOnly=TestExactArrayOfBasicType::test TestExactArrayOfBasicType
 *
 */


public class TestExactArrayOfBasicType {
    public static void test() {
        int[][][][][] array = new int[1][2][3][4][5];

        for (int i = 0; i < 50_000; ++i) {
            array[0] = new int[0][1][2][3];
        }
    }

    public static void main(String args[]) {
        test();
    }
}
